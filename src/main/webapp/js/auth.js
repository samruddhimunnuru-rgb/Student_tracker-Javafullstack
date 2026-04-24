document.addEventListener('DOMContentLoaded', () => {
    
    // Check if user is already logged in
    fetch('/StudentTracker/auth/session')
        .then(response => response.json())
        .then(data => {
            if (data.loggedIn && !window.location.pathname.endsWith('index.html')) {
                window.location.href = 'index.html';
            } else if (!data.loggedIn && window.location.pathname.endsWith('index.html')) {
                window.location.href = 'login.html';
            }
        });

    const loginForm = document.getElementById('login-form');
    if (loginForm) {
        loginForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            fetch('/StudentTracker/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password })
            })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'success') {
                    window.location.href = 'index.html';
                } else {
                    document.getElementById('error-message').innerText = data.message;
                }
            });
        });
    }

    const registerForm = document.getElementById('register-form');
    if (registerForm) {
        registerForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const name = document.getElementById('name').value;
            const email = document.getElementById('email').value;
            const password = document.getElementById('password').value;

            fetch('/StudentTracker/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, password })
            })
            .then(res => res.json())
            .then(data => {
                if (data.status === 'success') {
                    window.location.href = 'login.html';
                } else {
                    document.getElementById('error-message').innerText = data.message;
                }
            });
        });
    }
});
