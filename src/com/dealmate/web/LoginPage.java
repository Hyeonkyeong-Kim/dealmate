package com.dealmate.web;

public class LoginPage {
    public static String render() {
        return """
                <!doctype html>
                <html lang=\"ko\">
                <head>
                    <meta charset=\"UTF-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
                    <title>DealMate Login</title>
                    <style>
                        @keyframes slideUp {
                            from { opacity: 0; transform: translateY(20px); }
                            to { opacity: 1; transform: translateY(0); }
                        }
                        @keyframes fadeIn {
                            from { opacity: 0; }
                            to { opacity: 1; }
                        }
                        @keyframes slideDown {
                            from { opacity: 0; transform: translateY(-10px); }
                            to { opacity: 1; transform: translateY(0); }
                        }
                        html, body {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                            width: 100%;
                            height: 100vh;
                            overflow: hidden;
                            background: #F2F2F7;
                            font-family: 'Noto Sans KR', Arial, sans-serif;
                        }
                        * { box-sizing: border-box; }
                        body {
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            padding: 24px 12px;
                        }
                        .phone-frame {
                            width: 390px;
                            height: 760px;
                            background-color: #FFFFFF;
                            border-radius: 28px;
                            box-shadow: 0 18px 45px rgba(0, 0, 0, 0.12);
                            overflow: hidden;
                        }
                        .app-wrapper {
                            display: flex;
                            flex-direction: column;
                            height: 760px;
                            width: 100%;
                            background-color: #FFFFFF;
                        }
                        .login-container {
                            flex: 1;
                            display: flex;
                            flex-direction: column;
                            justify-content: space-between;
                            padding: 56px 20px 30px;
                        }
                        .top-section { text-align: center; animation: fadeIn 0.5s ease forwards; }
                        .logo {
                            font-size: 28px;
                            font-weight: 700;
                            margin: 0 0 8px 0;
                            letter-spacing: -0.5px;
                        }
                        .logo-deal { color: #007AFF; }
                        .logo-mate { color: #34C759; }
                        .subtitle {
                            font-size: 14px;
                            color: #999999;
                            margin: 0;
                            margin-bottom: 40px;
                        }
                        .login-form {
                            animation: slideUp 0.5s ease 0.15s forwards;
                        }
                        .form-group {
                            display: flex;
                            flex-direction: column;
                            margin-bottom: 12px;
                        }
                        .form-input {
                            width: 100%;
                            padding: 14px 16px;
                            background-color: #F2F2F7;
                            border: 1px solid #F2F2F7;
                            border-radius: 10px;
                            font-size: 14px;
                            color: #333333;
                            outline: none;
                            transition: all 0.2s;
                        }
                        .form-input:focus {
                            border-color: #007AFF;
                            background-color: #FFFFFF;
                            box-shadow: 0 0 0 3px rgba(0, 122, 255, 0.1);
                        }
                        .form-input::placeholder { color: #999999; }
                        .center-text {
                            font-size: 12px;
                            color: #999999;
                            text-align: center;
                            margin: 20px 0;
                            line-height: 1.4;
                        }
                        .button-group {
                            display: flex;
                            flex-direction: column;
                            gap: 10px;
                            margin-bottom: 20px;
                        }
                        .login-button, .signup-button {
                            width: 100%;
                            padding: 14px 16px;
                            border-radius: 10px;
                            font-size: 16px;
                            font-weight: 700;
                            cursor: pointer;
                            transition: all 0.2s;
                        }
                        .login-button {
                            background-color: #007AFF;
                            border: none;
                            color: #FFFFFF;
                        }
                        .signup-button {
                            background-color: transparent;
                            border: 2px solid #007AFF;
                            color: #007AFF;
                        }
                        .login-button:active, .signup-button:active, .error-button:active {
                            transform: scale(0.98);
                            opacity: 0.9;
                        }
                        .hint {
                            font-size: 11px;
                            color: #AAAAAA;
                            text-align: center;
                            line-height: 1.6;
                        }
                        .modal-overlay {
                            position: fixed;
                            top: 0;
                            left: 0;
                            right: 0;
                            bottom: 0;
                            background-color: rgba(0, 0, 0, 0.4);
                            display: none;
                            align-items: center;
                            justify-content: center;
                            z-index: 1000;
                            padding: 20px;
                        }
                        .modal-overlay.active { display: flex; }
                        .error-popup {
                            background-color: #FFFFFF;
                            border-radius: 16px;
                            padding: 24px;
                            width: 100%;
                            max-width: 280px;
                            text-align: center;
                            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
                            animation: slideDown 0.3s ease forwards;
                        }
                        .error-icon {
                            width: 48px;
                            height: 48px;
                            border-radius: 50%;
                            background: #FFECEC;
                            color: #FF6B6B;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            margin: 0 auto 16px;
                            font-size: 28px;
                            font-weight: 700;
                        }
                        .error-title {
                            font-size: 16px;
                            font-weight: 700;
                            color: #333333;
                            margin: 0 0 8px 0;
                        }
                        .error-message {
                            font-size: 14px;
                            color: #666666;
                            margin: 0 0 20px 0;
                            line-height: 1.4;
                        }
                        .error-button {
                            width: 100%;
                            padding: 12px 16px;
                            background-color: #007AFF;
                            border: none;
                            border-radius: 8px;
                            font-size: 14px;
                            font-weight: 600;
                            color: #FFFFFF;
                            cursor: pointer;
                        }
                    </style>
                </head>
                <body>
                    <main class=\"phone-frame\">
                        <div class=\"app-wrapper\">
                            <div class=\"login-container\">
                                <div class=\"top-section\">
                                    <h1 class=\"logo\"><span class=\"logo-deal\">DEAL</span><span class=\"logo-mate\">MATE</span></h1>
                                    <p class=\"subtitle\">우리 동네 공동구매 플랫폼</p>
                                </div>

                                <form class=\"login-form\" id=\"login-form\">
                                    <div class=\"form-group\">
                                        <input type=\"text\" class=\"form-input\" id=\"input-id\" name=\"id\" placeholder=\"아이디 또는 이메일을 입력하세요\">
                                    </div>
                                    <div class=\"form-group\">
                                        <input type=\"password\" class=\"form-input\" id=\"input-password\" name=\"pw\" placeholder=\"비밀번호를 입력하세요\">
                                    </div>
                                    <p class=\"center-text\">동네 이웃과 함께 저렴하게 구매하세요</p>
                                    <div class=\"button-group\">
                                        <button type=\"submit\" class=\"login-button\">로그인</button>
                                        <button type=\"button\" class=\"signup-button\" onclick=\"location.href='/register'\">회원가입</button>
                                    </div>
                                </form>

                                <p class=\"hint\">테스트 계정: demo / demo123<br>테스트 계정: demo1 / demo123<br>관리자 계정: admin / admin123</p>
                            </div>
                        </div>
                    </main>

                    <div class=\"modal-overlay\" id=\"error-modal\">
                        <div class=\"error-popup\">
                            <div class=\"error-icon\">!</div>
                            <h2 class=\"error-title\">로그인 실패</h2>
                            <p class=\"error-message\" id=\"error-message\">아이디 또는 비밀번호가 틀렸습니다.</p>
                            <button class=\"error-button\" onclick=\"closeErrorModal()\">확인</button>
                        </div>
                    </div>

                    <script>
                        const loginForm = document.getElementById('login-form');
                        const errorModal = document.getElementById('error-modal');
                        const errorMessage = document.getElementById('error-message');

                        loginForm.addEventListener('submit', async function (event) {
                            event.preventDefault();
                            const id = document.getElementById('input-id').value.trim();
                            const pw = document.getElementById('input-password').value.trim();

                            if (!id || !pw) {
                                showErrorModal('아이디 또는 비밀번호가 틀렸습니다.');
                                return;
                            }

                            const response = await fetch('/api/login', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                                body: new URLSearchParams({ id, pw })
                            });
                            const result = await response.json();

                            if (result.success) {
                                location.href = result.next;
                            } else {
                                showErrorModal(result.message || '아이디 또는 비밀번호가 틀렸습니다.');
                            }
                        });

                        function showErrorModal(message) {
                            errorMessage.textContent = message;
                            errorModal.classList.add('active');
                        }

                        function closeErrorModal() {
                            errorModal.classList.remove('active');
                        }
                    </script>
                </body>
                </html>
                """;
    }
}
