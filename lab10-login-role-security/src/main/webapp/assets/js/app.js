document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            try {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            } catch (e) {}
        }, 5000);
    });

    // Confirm delete helper
    window.confirmDelete = function(event, message) {
        if (!confirm(message || 'Bạn có chắc chắn muốn xóa bản ghi này không? Thao tác này không thể hoàn tác!')) {
            event.preventDefault();
            return false;
        }
        return true;
    };
});
