document.addEventListener("DOMContentLoaded", () => {
    const contractDate = document.getElementById("staffIndt");
    const staffNo = document.getElementById("staffNo");
    const birthDate = document.getElementById("staffBirthdt");
    const age = document.getElementById("staffAge");
    const staffClass = document.getElementById("staffCls");
    const company = document.getElementById("staffCompany");
    const postalSearch = document.getElementById("postalSearch");

    async function refreshStaffNo() {
        if (!contractDate?.value || !staffNo || staffNo.value) {
            return;
        }
        const response = await fetch(`/staff/generate-number?staffIndt=${encodeURIComponent(contractDate.value)}`);
        if (response.ok) {
            staffNo.value = await response.text();
        }
    }

    function refreshAge() {
        if (!birthDate?.value || !age) {
            return;
        }
        const birth = new Date(`${birthDate.value}T00:00:00`);
        const today = new Date();
        let years = today.getFullYear() - birth.getFullYear();
        const beforeBirthday = today.getMonth() < birth.getMonth()
            || (today.getMonth() === birth.getMonth() && today.getDate() < birth.getDate());
        if (beforeBirthday) {
            years--;
        }
        age.value = Math.max(0, years);
    }

    function refreshCompanyState() {
        if (!staffClass || !company) {
            return;
        }
        const internal = staffClass.value === "01" || staffClass.value === "02";
        company.disabled = internal;
        if (internal) {
            company.value = "";
        }
    }

    async function searchPostalAddress() {
        const postalCode = document.getElementById("staffPostno");
        const address = document.getElementById("staffAddress");
        const normalized = postalCode?.value.replace(/\D/g, "");
        if (!normalized || normalized.length !== 7) {
            window.alert("郵便番号は7桁で入力してください。");
            return;
        }
        const response = await fetch(`https://zipcloud.ibsnet.co.jp/api/search?zipcode=${normalized}`);
        const data = await response.json();
        if (!data.results?.length) {
            window.alert("住所が見つかりませんでした。");
            return;
        }
        const result = data.results[0];
        address.value = `${result.address1}${result.address2}${result.address3}`;
    }

    contractDate?.addEventListener("change", refreshStaffNo);
    birthDate?.addEventListener("change", refreshAge);
    staffClass?.addEventListener("change", refreshCompanyState);
    postalSearch?.addEventListener("click", searchPostalAddress);

    refreshStaffNo();
    refreshAge();
    refreshCompanyState();
});
