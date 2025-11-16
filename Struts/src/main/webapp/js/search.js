// Pagination functions
function goToPage(action) {
    var currentPageInput = document.getElementById('currentPage');
    var totalPagesInput = document.getElementById('totalPages');

    var currentPage = parseInt(currentPageInput.value) || 1;
    var totalPages = parseInt(totalPagesInput.value) || 1;

    if (action === 'first') {
        currentPageInput.value = 1;
    } else if (action === 'previous') {
        currentPageInput.value = Math.max(1, currentPage - 1);
    } else if (action === 'next') {
        currentPageInput.value = Math.min(totalPages, currentPage + 1);
    } else if (action === 'last') {
        currentPageInput.value = totalPages;
    }

    document.getElementById('paginationForm').submit();
}

// Select all checkboxes
function toggleSelectAll() {
    var selectAll = document.getElementById('selectAll');
    var checkboxes = document.getElementsByClassName('customer-checkbox');

    for (var i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = selectAll.checked;
    }

    updateDeleteButton();
}

// Update delete button state
function updateDeleteButton() {
    var checkboxes = document.getElementsByClassName('customer-checkbox');
    var deleteBtn = document.getElementById('deleteBtn');
    var selectAll = document.getElementById('selectAll');
    var hasChecked = false;
    var allChecked = true;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            hasChecked = true;
        } else {
            allChecked = false;
        }
    }

    // Enable/disable delete button
    deleteBtn.disabled = !hasChecked;

    // Update select all checkbox
    if (checkboxes.length > 0) {
        selectAll.checked = allChecked;
    }
}

// Delete selected customers
function deleteSelected() {
    var checkboxes = document.getElementsByClassName('customer-checkbox');
    var selectedCount = 0;

    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
            selectedCount++;
        }
    }

    if (selectedCount === 0) {
        alert('Vui lòng chọn ít nhất một khách hàng để xóa!');
        return;
    }

    var confirmMsg = 'Bạn có chắc chắn muốn xóa ' + selectedCount + ' khách hàng đã chọn?';

    if (confirm(confirmMsg)) {
        document.getElementById('deleteForm').submit();
    }
}