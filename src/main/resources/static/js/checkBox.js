document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');
    const agreeAll = document.getElementById('agreeAll');

    // 1. register.html에 실제로 존재하는 필수 약관 ID 목록입니다.
    const requiredTerms = document.querySelectorAll('#terms1, #terms2, #terms4, #terms5');

    // 2. '전체 동의'를 클릭했을 때의 동작
    agreeAll.addEventListener('change', function() {
        const isChecked = this.checked;
        requiredTerms.forEach(function(checkbox) {
            checkbox.checked = isChecked;
        });
    });

    // 3. 개별 약관을 클릭했을 때 '전체 동의' 상태를 업데이트하는 함수
    function updateAgreeAllStatus() {
        // Array.from()으로 NodeList를 배열로 변환하고 every()를 사용합니다.
        // 모든 개별 약관이 체크되었는지 확인합니다.
        const allChecked = Array.from(requiredTerms).every(checkbox => checkbox.checked);
        agreeAll.checked = allChecked;
    }

    // 4. 모든 개별 약관 체크박스에 3번 함수를 이벤트 리스너로 등록합니다.
    requiredTerms.forEach(function(checkbox) {
        checkbox.addEventListener('change', updateAgreeAllStatus);
    });

    // 5. '동의하고 가입하기' 버튼을 눌렀을 때(submit)의 동작
    form.addEventListener('submit', function(event) {
        // 1번 목록의 필수 약관이 모두 체크되었는지 다시 확인합니다.
        const allRequiredChecked = Array.from(requiredTerms).every(checkbox => checkbox.checked);

        if (!allRequiredChecked) {
            alert('필수 약관에 모두 동의해주세요.');
            event.preventDefault(); // 폼 전송을 막습니다.
        }
    });
});