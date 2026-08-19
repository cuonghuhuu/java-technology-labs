document.addEventListener("DOMContentLoaded", function () {

    setupDeleteConfirmation();
    setupAutoHideAlerts();
    setupFormValidation();

});


function setupDeleteConfirmation() {

    const deleteButtons =
        document.querySelectorAll(".js-delete");

    deleteButtons.forEach(function (button) {

        button.addEventListener("click", function (event) {

            const studentName =
                button.dataset.name || "sinh viên này";

            const confirmed = confirm(
                "Bạn có chắc muốn xóa \"" +
                studentName +
                "\" không?\n\n" +
                "Thao tác này không thể hoàn tác."
            );

            if (!confirmed) {
                event.preventDefault();
            }

        });

    });
}


function setupAutoHideAlerts() {

    const alerts =
        document.querySelectorAll(".js-alert");

    alerts.forEach(function (alert) {

        setTimeout(function () {

            alert.classList.add("hide");

            setTimeout(function () {
                alert.remove();
            }, 400);

        }, 3500);

    });
}


function setupFormValidation() {

    const form =
        document.getElementById("studentForm");

    if (!form) {
        return;
    }

    form.addEventListener("submit", function (event) {

        const studentCode =
            document.getElementById("maSinhVien");

        const studentName =
            document.getElementById("hoTen");

        const email =
            document.getElementById("email");

        const className =
            document.getElementById("lop");


        if (studentCode.value.trim().length < 4) {

            alert(
                "Mã sinh viên phải có ít nhất 4 ký tự."
            );

            studentCode.focus();

            event.preventDefault();

            return;
        }


        if (studentName.value.trim().length < 2) {

            alert(
                "Họ tên sinh viên không hợp lệ."
            );

            studentName.focus();

            event.preventDefault();

            return;
        }


        if (!email.value.includes("@")) {

            alert(
                "Vui lòng nhập email hợp lệ."
            );

            email.focus();

            event.preventDefault();

            return;
        }


        if (className.value.trim() === "") {

            alert(
                "Vui lòng nhập lớp."
            );

            className.focus();

            event.preventDefault();
        }

    });
}