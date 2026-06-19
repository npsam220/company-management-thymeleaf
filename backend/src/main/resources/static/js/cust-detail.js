document.addEventListener("DOMContentLoaded", () => {
    const addButton = document.getElementById("addManagerRow");
    const rowsContainer = document.getElementById("managerRows");

    if (addButton && rowsContainer) {
        function createManagerRow() {
            const row = document.createElement("tr");
            row.className = "cust-detail-manager-row";
            row.innerHTML = `
                <td>
                    <input type="hidden" data-manager-field="managerCd" value="">
                    <input type="hidden" data-manager-field="managerChrcd" value="">
                    <input type="text" data-manager-field="managerNm" required>
                </td>
                <td><input type="tel" data-manager-field="managerPno"></td>
                <td><input type="email" data-manager-field="managerMail"></td>
                <td>
                    <select data-manager-field="managerJob">
                        <option value="">選択</option>
                        <option value="01">営業</option>
                        <option value="02">総務</option>
                        <option value="03">その他</option>
                    </select>
                </td>
                <td><input type="text" data-manager-field="managerRmk"></td>
                <td>
                    <button class="button button--spec cust-detail-manager-remove"
                        type="button"
                        aria-label="担当者を削除">×</button>
                </td>
            `;
            return row;
        }

        function normalizeManagerFields() {
            rowsContainer.querySelectorAll(".cust-detail-manager-row").forEach((row, index) => {
                row.querySelectorAll("input, select").forEach((field) => {
                    const property = field.dataset.managerField
                        || field.name?.match(/managers\[\d+\]\.(.+)/)?.[1];
                    if (!property) {
                        return;
                    }
                    field.dataset.managerField = property;
                    field.name = `managers[${index}].${property}`;
                    field.id = `managers${index}.${property}`;
                });
            });
        }

        addButton.addEventListener("click", () => {
            document.getElementById("managerEmptyRow")?.remove();
            const row = createManagerRow();
            rowsContainer.appendChild(row);
            normalizeManagerFields();
            row.querySelector('[data-manager-field="managerNm"]')?.focus();
        });

        rowsContainer.addEventListener("click", (event) => {
            const removeButton = event.target.closest(".cust-detail-manager-remove");
            if (!removeButton) {
                return;
            }
            removeButton.closest(".cust-detail-manager-row")?.remove();
            normalizeManagerFields();
        });

        normalizeManagerFields();
    }

    const custType = document.getElementById("custType");
    const openBankSearch = document.getElementById("openBankSearch");
    const closeBankSearch = document.getElementById("closeBankSearch");
    const bankSearchDialog = document.getElementById("bankSearchDialog");
    const bankSearchKeyword = document.getElementById("bankSearchKeyword");
    const bankSearchRows = document.getElementById("bankSearchRows");
    const bankSearchEmpty = document.getElementById("bankSearchEmpty");
    const bankCd = document.getElementById("bankCd");
    const bankChrcd = document.getElementById("bankChrcd");
    const bankName = document.getElementById("custBankNm");
    const branchName = document.getElementById("custBranchNm");
    const branchCode = document.getElementById("custBranchCd");
    const accountNumber = document.getElementById("custAccountno");
    const nominee = document.getElementById("custNominee");
    const depositType = document.getElementById("custDeposittype");
    const depositTypeMaster = document.getElementById("custDeposittypeMaster");
    const masterFields = [bankName, branchName, branchCode, accountNumber, nominee];

    if (!custType || !openBankSearch || !bankSearchDialog || !bankSearchRows) {
        return;
    }

    function applySelectedBank(button) {
        bankCd.value = button.dataset.bankCd || "";
        bankChrcd.value = button.dataset.bankChrcd || "";
        bankName.value = button.dataset.bankNm || "";
        branchName.value = button.dataset.branchNm || "";
        branchCode.value = button.dataset.branchCd || "";
        accountNumber.value = button.dataset.accountNo || "";
        nominee.value = button.dataset.nominee || "";
        depositType.value = button.dataset.depositType || "";
        depositTypeMaster.value = depositType.value;
    }

    function filterBankRows() {
        const keyword = bankSearchKeyword.value.trim().toLowerCase();
        let visibleCount = 0;
        bankSearchRows.querySelectorAll("tr").forEach((row) => {
            const visible = !keyword
                || (row.dataset.searchText || "").toLowerCase().includes(keyword);
            row.hidden = !visible;
            if (visible) {
                visibleCount += 1;
            }
        });
        bankSearchEmpty.hidden = visibleCount !== 0;
    }

    function updateBankInputMode() {
        const usesMaster = custType.value === "01";
        openBankSearch.hidden = !["01", "02", "03"].includes(custType.value);
        if (!usesMaster) {
            bankCd.value = "";
            bankChrcd.value = "";
        }

        masterFields.forEach((field) => {
            field.readOnly = usesMaster;
            field.classList.toggle("cust-detail-master-locked", usesMaster);
        });

        depositType.disabled = usesMaster;
        depositType.classList.toggle("cust-detail-master-locked", usesMaster);
        depositTypeMaster.disabled = !usesMaster;
        depositTypeMaster.name = usesMaster ? "custDeposittype" : "";
        depositTypeMaster.value = usesMaster ? depositType.value : "";
    }

    openBankSearch.addEventListener("click", () => {
        bankSearchKeyword.value = "";
        filterBankRows();
        // モーダルを表示してからフォーカスを当てないと、モーダル外の要素にフォーカスが当たってしまう
        bankSearchDialog.showModal();
        // フォーカスを当てる前にモーダルが表示される必要があるため、setTimeoutで遅らせる
        bankSearchKeyword.focus();
    });
    closeBankSearch?.addEventListener("click", () => bankSearchDialog.close());
    bankSearchDialog.addEventListener("click", (event) => {
        if (event.target === bankSearchDialog) {
            bankSearchDialog.close();
        }
    });
    bankSearchKeyword.addEventListener("input", filterBankRows);
    bankSearchRows.addEventListener("click", (event) => {
        const selectButton = event.target.closest(".cust-detail-bank-select");
        if (!selectButton) {
            return;
        }
        applySelectedBank(selectButton);
        bankSearchDialog.close();
    });
    custType.addEventListener("change", updateBankInputMode);
    updateBankInputMode();
});
