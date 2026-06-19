document.addEventListener("DOMContentLoaded", () => {
    const contractDate = document.getElementById("staffIndt");
    const staffNo = document.getElementById("staffNo");
    const birthDate = document.getElementById("staffBirthdt");
    const age = document.getElementById("staffAge");
    const staffClass = document.getElementById("staffCls");
    const company = document.getElementById("staffCompany");
    const country = document.getElementById("staffCountry");
    const postalCode = document.getElementById("staffPostno");
    const address = document.getElementById("staffAddress");
    const residenceCard = document.getElementById("staffResidencecard");
    const residenceCardHidden = document.getElementById("staffResidencecardHidden");
    const residenceType = document.getElementById("staffRestype");
    const residenceTypeHidden = document.getElementById("staffRestypeHidden");
    const residenceExpire = document.getElementById("staffCardenddt");
    const residenceExpireHidden = document.getElementById("staffCardenddtHidden");
    const specialtyField = document.getElementById("staffRmk2");
    const specialtyCheckboxes = Array.from(document.querySelectorAll(".staff-detail-specialty-checkbox"));
    let postalLookupTimer;
    let lastPostalCode = "";
    let lastAddress = "";
    const specialtyOptions = [
        "ローコード",
        "バックエンド",
        "フロントエンド",
        "インフラ",
        "DBA",
        "AI",
        "BSE",
        "PMO",
        "PM",
        "リーダー",
        "要件定義",
        "基本設計",
        "詳細設計",
        "製造",
        "テスト"
    ];

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

    function syncLockedField(field, hiddenField, locked) {
        if (!field || !hiddenField) {
            return;
        }

        if (locked) {
            hiddenField.name = field.name;
            hiddenField.value = "";
            field.dataset.originalName = field.name;
            field.name = "";
            field.value = "";
            field.disabled = true;
            field.classList.add("staff-detail-master-locked");
        } else {
            if (field.dataset.originalName) {
                field.name = field.dataset.originalName;
            }
            field.disabled = false;
            field.classList.remove("staff-detail-master-locked");
            hiddenField.name = "";
            hiddenField.value = "";
        }
    }

    function refreshResidenceState() {
        if (!country) {
            return;
        }
        const locked = country.value === "02";
        syncLockedField(residenceCard, residenceCardHidden, locked);
        syncLockedField(residenceType, residenceTypeHidden, locked);
        syncLockedField(residenceExpire, residenceExpireHidden, locked);
    }

    function getSelectedSpecialties() {
        if (!specialtyField?.value) {
            return [];
        }

        return specialtyField.value
            .replace(/^得意分野：/, "")
            .split(",")
            .map((item) => item.trim())
            .filter(Boolean);
    }

    function syncSpecialtyField() {
        if (!specialtyField) {
            return;
        }

        const selected = specialtyOptions.filter((option) =>
            specialtyCheckboxes.some((checkbox) => checkbox.value === option && checkbox.checked)
        );

        specialtyField.value = selected.join(",");
    }

    function refreshSpecialtyChecks() {
        const selected = getSelectedSpecialties();

        specialtyCheckboxes.forEach((checkbox) => {
            checkbox.checked = selected.includes(checkbox.value);
        });
    }

    async function searchPostalAddress(forceAlert = false) {
        const normalized = postalCode?.value.replace(/\D/g, "");
        if (!postalCode || !address || !normalized || normalized.length !== 7) {
            return;
        }

        if (normalized === lastPostalCode && address.value === lastAddress) {
            return;
        }

        const response = await fetch(`https://zipcloud.ibsnet.co.jp/api/search?zipcode=${normalized}`);
        const data = await response.json();
        if (!data.results?.length) {
            if (forceAlert) {
                window.alert("住所が見つかりませんでした。");
            }
            return;
        }
        const result = data.results[0];
        lastPostalCode = normalized;
        lastAddress = `${result.address1}${result.address2}${result.address3}`;
        address.value = lastAddress;
    }

    function queuePostalSearch() {
        const normalized = postalCode?.value.replace(/\D/g, "") ?? "";
        if (normalized.length !== 7) {
            window.clearTimeout(postalLookupTimer);
            return;
        }

        window.clearTimeout(postalLookupTimer);
        postalLookupTimer = window.setTimeout(() => {
            searchPostalAddress(false);
        }, 300);
    }

    contractDate?.addEventListener("change", refreshStaffNo);
    birthDate?.addEventListener("change", refreshAge);
    staffClass?.addEventListener("change", refreshCompanyState);
    country?.addEventListener("change", refreshResidenceState);
    postalCode?.addEventListener("input", queuePostalSearch);
    postalCode?.addEventListener("change", () => searchPostalAddress(true));
    specialtyCheckboxes.forEach((checkbox) => {
        checkbox.addEventListener("change", syncSpecialtyField);
    });

    refreshStaffNo();
    refreshAge();
    refreshCompanyState();
    refreshResidenceState();
    refreshSpecialtyChecks();
});
