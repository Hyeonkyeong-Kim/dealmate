package com.dealmate.web;

public class RegisterPage {
    public static String render() {
        return """
                <!doctype html>
                <html lang=\"ko\">
                <head>
                    <meta charset=\"UTF-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
                    <title>DealMate Register</title>
                    <style>
                        @keyframes slideDown {
                            from { opacity: 0; transform: translateY(-10px); }
                            to { opacity: 1; transform: translateY(0); }
                        }
                        html, body {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                            width: 100%;
                            min-height: 100%;
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
                            width: min(390px, 100%);
                            min-height: 760px;
                            background-color: #FFFFFF;
                            border-radius: 28px;
                            box-shadow: 0 18px 45px rgba(0, 0, 0, 0.12);
                            overflow: hidden;
                        }
                        .app-wrapper {
                            display: flex;
                            flex-direction: column;
                            min-height: 760px;
                            width: 100%;
                            background-color: #FFFFFF;
                        }
                        .register-header {
                            display: flex;
                            align-items: center;
                            gap: 12px;
                            padding: 18px 20px;
                            background-color: #FFFFFF;
                            border-bottom: 1px solid #F2F2F7;
                        }
                        .back-button {
                            background: none;
                            border: none;
                            font-size: 24px;
                            cursor: pointer;
                            color: #333333;
                            padding: 0;
                            width: 32px;
                            height: 32px;
                        }
                        .register-title {
                            font-size: 20px;
                            font-weight: 700;
                            color: #333333;
                            margin: 0;
                            flex: 1;
                        }
                        .register-content {
                            flex: 1;
                            padding: 24px 20px 10px;
                        }
                        .form-group { margin-bottom: 16px; }
                        .form-label {
                            font-size: 13px;
                            font-weight: 600;
                            color: #666666;
                            margin-bottom: 6px;
                            display: block;
                        }
                        .form-input {
                            width: 100%;
                            padding: 12px 14px;
                            background-color: #F8F8F8;
                            border: 1px solid #E0E0E0;
                            border-radius: 8px;
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
                        .guide-text {
                            font-size: 13px;
                            color: #999999;
                            margin: 20px 0 12px;
                            text-align: center;
                        }
                        .submit-button-container { padding: 16px 20px 24px; }
                        .submit-button {
                            width: 100%;
                            padding: 14px;
                            background-color: #007AFF;
                            border: none;
                            border-radius: 10px;
                            font-size: 15px;
                            font-weight: 700;
                            color: #FFFFFF;
                            cursor: pointer;
                            transition: all 0.2s;
                        }
                        .submit-button:active, .modal-button:active {
                            transform: scale(0.98);
                            opacity: 0.9;
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
                        .modal-overlay.show { display: flex; }
                        .modal-content {
                            background-color: #FFFFFF;
                            border-radius: 14px;
                            padding: 20px;
                            width: 100%;
                            max-width: 280px;
                            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
                            animation: slideDown 0.3s ease forwards;
                        }
                        .modal-title {
                            font-size: 16px;
                            font-weight: 700;
                            color: #333333;
                            margin: 0 0 8px 0;
                            text-align: center;
                        }
                        .modal-message {
                            font-size: 14px;
                            color: #666666;
                            margin: 0 0 16px 0;
                            text-align: center;
                            line-height: 1.4;
                        }
                        .modal-button {
                            width: 100%;
                            padding: 10px;
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
                            <div class=\"register-header\">
                                <button class=\"back-button\" onclick=\"location.href='/login'\">←</button>
                                <h1 class=\"register-title\">회원가입</h1>
                            </div>

                            <div class=\"register-content\">
                                <form id=\"register-form\">
                                    <div class=\"form-group\">
                                        <label class=\"form-label\">아이디</label>
                                        <input type=\"text\" class=\"form-input\" id=\"input-username\" placeholder=\"아이디를 입력하세요\">
                                    </div>
                                    <div class=\"form-group\">
                                        <label class=\"form-label\">비밀번호</label>
                                        <input type=\"password\" class=\"form-input\" id=\"input-password\" placeholder=\"비밀번호를 입력하세요\">
                                    </div>
                                    <div class=\"form-group\">
                                        <label class=\"form-label\">비밀번호 확인</label>
                                        <input type=\"password\" class=\"form-input\" id=\"input-password-confirm\" placeholder=\"비밀번호를 다시 입력하세요\">
                                    </div>
                                    <div class=\"form-group\">
                                        <label class=\"form-label\">이메일</label>
                                        <input type=\"email\" class=\"form-input\" id=\"input-email\" placeholder=\"이메일을 입력하세요\">
                                    </div>
                                </form>
                            </div>

                            <div class=\"submit-button-container\">
                                <div class=\"guide-text\">회원가입 후 동네 인증을 진행해주세요.</div>
                                <button class=\"submit-button\" id=\"submit-btn\">회원가입 완료</button>
                            </div>
                        </div>
                    </main>

                    <div class=\"modal-overlay\" id=\"message-modal\">
                        <div class=\"modal-content\">
                            <h2 class=\"modal-title\" id=\"modal-title\">입력 오류</h2>
                            <p class=\"modal-message\" id=\"modal-message\">미기입된 내용이 있습니다.</p>
                            <button class=\"modal-button\" id=\"modal-confirm\">확인</button>
                        </div>
                    </div>

                    <script>
                        const submitButton = document.getElementById('submit-btn');
                        const messageModal = document.getElementById('message-modal');
                        const modalTitle = document.getElementById('modal-title');
                        const modalMessage = document.getElementById('modal-message');
                        const modalConfirm = document.getElementById('modal-confirm');
                        let successNext = null;

                        submitButton.addEventListener('click', async function (event) {
                            event.preventDefault();
                            const userId = document.getElementById('input-username').value.trim();
                            const password = document.getElementById('input-password').value.trim();
                            const passwordConfirm = document.getElementById('input-password-confirm').value.trim();
                            const email = document.getElementById('input-email').value.trim();

                            if (!userId || !password || !passwordConfirm || !email) {
                                showModal('입력 오류', '미기입된 내용이 있습니다.', null);
                                return;
                            }
                            if (password !== passwordConfirm) {
                                showModal('입력 오류', '비밀번호가 일치하지 않습니다.', null);
                                return;
                            }
                            if (!isValidEmail(email)) {
                                showModal('입력 오류', '올바른 이메일 형식이 아닙니다.', null);
                                return;
                            }

                            const response = await fetch('/api/register', {
                                method: 'POST',
                                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' },
                                body: new URLSearchParams({ userId, password, passwordConfirm, email })
                            });
                            const result = await response.json();

                            if (result.success) {
                                showModal('회원가입 완료', result.message || '회원가입 되었습니다.', result.next || '/login');
                            } else {
                                showModal('입력 오류', result.message || '미기입된 내용이 있습니다.', null);
                            }
                        });

                        modalConfirm.addEventListener('click', function () {
                            messageModal.classList.remove('show');
                            if (successNext) {
                                location.href = successNext;
                            }
                        });

                        function isValidEmail(email) {
                            return /^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$/.test(email);
                        }

                        function showModal(title, message, next) {
                            modalTitle.textContent = title;
                            modalMessage.textContent = message;
                            successNext = next;
                            messageModal.classList.add('show');
                        }
                    </script>
                </body>
                </html>
                """;
    }
}
