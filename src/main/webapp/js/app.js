let taskChartInstance = null;
let placementChartInstance = null;
let allTasks = [];
let placementData = null;

document.addEventListener('DOMContentLoaded', () => {
    // Check session again and load user info
    fetch('/StudentTracker/auth/session')
        .then(res => res.json())
        .then(data => {
            if (data.loggedIn) {
                document.getElementById('welcome-msg').innerText = `Welcome, ${data.userName} 👋`;
                loadData();
            }
        });

    // Task Form
    document.getElementById('task-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const task = {
            subject: document.getElementById('task-subject').value,
            topic: document.getElementById('task-topic').value,
            deadline: document.getElementById('task-deadline').value,
            priority: document.getElementById('task-priority').value,
            status: 'Pending'
        };

        fetch('/StudentTracker/api/tasks', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(task)
        }).then(res => res.json()).then(data => {
            if (data.status === 'success') {
                document.getElementById('task-form').reset();
                loadTasks(); // Reload tasks
            }
        });
    });

    // Placement Form
    document.getElementById('placement-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const placement = {
            aptitudeScore: parseInt(document.getElementById('score-aptitude').value),
            codingScore: parseInt(document.getElementById('score-coding').value),
            interviewScore: parseInt(document.getElementById('score-interview').value)
        };

        fetch('/StudentTracker/api/placement', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(placement)
        }).then(res => res.json()).then(data => {
            if (data.status === 'success') {
                loadPlacement();
            }
        });
    });
});

function loadData() {
    loadTasks();
    loadPlacement();
    loadRecommendation();
}

function loadTasks() {
    fetch('/StudentTracker/api/tasks')
        .then(res => res.json())
        .then(tasks => {
            allTasks = tasks;
            renderTasks();
            updateDashboardStats();
            updateTaskChart();
        });
}

function loadPlacement() {
    fetch('/StudentTracker/api/placement')
        .then(res => res.json())
        .then(data => {
            placementData = data;
            
            document.getElementById('score-aptitude').value = data.aptitudeScore;
            document.getElementById('score-coding').value = data.codingScore;
            document.getElementById('score-interview').value = data.interviewScore;
            
            let avg = (data.aptitudeScore + data.codingScore + data.interviewScore) / 3;
            document.getElementById('readiness-score').innerText = Math.round(avg) + '%';
            
            updatePlacementChart();
        });
}

function loadRecommendation() {
    fetch('/StudentTracker/api/recommendation')
        .then(res => res.json())
        .then(data => {
            document.getElementById('ai-suggestion').innerText = data.recommendation;
            document.getElementById('ai-reason').innerText = data.reason;
        });
}

function renderTasks() {
    const tbody = document.getElementById('task-table-body');
    tbody.innerHTML = '';
    
    allTasks.forEach(task => {
        const tr = document.createElement('tr');
        
        // Formatting date
        const d = new Date(task.deadline);
        const dateStr = d.toLocaleDateString();
        
        const badgeClass = task.status === 'Completed' ? 'completed' : 'pending';
        
        tr.innerHTML = `
            <td>
                <div style="font-weight: 500;">${task.subject}</div>
                <div style="font-size: 12px; color: var(--text-secondary);">${task.topic}</div>
            </td>
            <td>${dateStr}</td>
            <td>${task.priority}</td>
            <td><span class="badge ${badgeClass}">${task.status}</span></td>
            <td>
                ${task.status === 'Pending' ? 
                  `<button onclick="updateTaskStatus(${task.id}, 'Completed')" class="btn" style="background:var(--success); color:white; padding:4px 8px; font-size:12px;">Mark Done</button>` : 
                  ''}
                <button onclick="deleteTask(${task.id})" class="btn" style="background:var(--danger); color:white; padding:4px 8px; font-size:12px; margin-left: 5px;">Delete</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function updateTaskStatus(taskId, status) {
    fetch('/StudentTracker/api/tasks', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ taskId, status })
    }).then(res => res.json()).then(data => {
        if(data.status === 'success') {
            loadData(); // reload everything
        }
    });
}

function deleteTask(id) {
    if(confirm('Are you sure you want to delete this task?')) {
        fetch(`/StudentTracker/api/tasks?id=${id}`, {
            method: 'DELETE'
        }).then(res => res.json()).then(data => {
            if(data.status === 'success') {
                loadData();
            }
        });
    }
}

function updateDashboardStats() {
    const total = allTasks.length;
    const completed = allTasks.filter(t => t.status === 'Completed').length;
    const pending = total - completed;
    
    document.getElementById('total-tasks').innerText = total;
    document.getElementById('pending-tasks').innerText = pending;
    document.getElementById('completed-tasks').innerText = completed;
}

function showSection(sectionId) {
    // Hide all sections
    ['dashboard', 'tasks', 'placement', 'analytics'].forEach(id => {
        document.getElementById(`section-${id}`).style.display = 'none';
    });
    
    // Update nav classes
    document.querySelectorAll('.nav-item').forEach(el => el.classList.remove('active'));
    event.currentTarget.classList.add('active');
    
    // Show selected section
    document.getElementById(`section-${sectionId}`).style.display = 'block';
}

function logout() {
    fetch('/StudentTracker/auth/logout', { method: 'POST' })
        .then(() => window.location.href = 'login.html');
}

// Chart.js updates
function updateTaskChart() {
    const completed = allTasks.filter(t => t.status === 'Completed').length;
    const pending = allTasks.length - completed;
    
    const ctx = document.getElementById('taskPieChart').getContext('2d');
    
    if (taskChartInstance) {
        taskChartInstance.destroy();
    }
    
    taskChartInstance = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Completed', 'Pending'],
            datasets: [{
                data: [completed, pending],
                backgroundColor: ['#0f7b6c', '#eb5757'],
                borderWidth: 0
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

function updatePlacementChart() {
    if (!placementData) return;
    
    const ctx = document.getElementById('placementRadarChart').getContext('2d');
    
    if (placementChartInstance) {
        placementChartInstance.destroy();
    }
    
    placementChartInstance = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: ['Aptitude', 'Coding', 'Interview'],
            datasets: [{
                label: 'Score Profile',
                data: [placementData.aptitudeScore, placementData.codingScore, placementData.interviewScore],
                backgroundColor: 'rgba(35, 131, 226, 0.2)',
                borderColor: '#2383e2',
                pointBackgroundColor: '#2383e2',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: '#2383e2'
            }]
        },
        options: {
            scales: {
                r: {
                    angleLines: { display: false },
                    suggestedMin: 0,
                    suggestedMax: 100
                }
            }
        }
    });
}
