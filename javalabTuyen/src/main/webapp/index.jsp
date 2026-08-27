<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bài Tập Công Nghệ Java - javalabTuyen</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">

    <div class="header">
        <h1>Chương Trình Tính Điểm & Xếp Loại Sinh Viên</h1>
        <p>Môn học: Công nghệ Java (Maven + Tomcat Web Application) | Đề tài: javalabTuyen</p>
    </div>

    <!-- Thông báo lỗi (Kiểm tra dữ liệu) -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">
            <strong>❌ Lỗi kiểm tra dữ liệu:</strong> ${errorMessage}
        </div>
    </c:if>

    <!-- Thông báo thành công -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <strong>✅ Thông báo:</strong> ${successMessage}
        </div>
    </c:if>

    <!-- Công thức tính điểm -->
    <div class="formula-box">
        <strong>📌 Quy tắc tính điểm & Xếp loại:</strong><br>
        • <strong>Điểm tổng kết</strong> = Chuyên cần × 10% + Giữa kỳ × 30% + Cuối kỳ × 60%<br>
        • <strong>Thang xếp loại:</strong> <span class="badge badge-A">A</span> ≥ 8.5 | <span class="badge badge-B">B</span> ≥ 7.0 | <span class="badge badge-C">C</span> ≥ 5.5 | <span class="badge badge-D">D</span> ≥ 4.0 | <span class="badge badge-F">F</span> &lt; 4.0<br>
        • <strong>Ràng buộc dữ liệu:</strong> Điểm số phải trong khoảng từ <strong>0.0 đến 10.0</strong>.
    </div>

    <!-- Form Nhập dữ liệu -->
    <div class="card">
        <h2 class="card-title">1. Nhập Dữ Liệu Sinh Viên</h2>
        <form action="${pageContext.request.contextPath}/student" method="post">
            <div class="form-grid">
                <div class="form-group">
                    <label for="studentId">Mã Sinh Viên (*):</label>
                    <input type="text" id="studentId" name="studentId" placeholder="Ví dụ: SV001" value="${studentId}" required>
                </div>

                <div class="form-group">
                    <label for="fullName">Họ và Tên (*):</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Ví dụ: Nguyễn Văn An" value="${fullName}" required>
                </div>

                <div class="form-group">
                    <label for="attendanceScore">Điểm Chuyên Cần (10%) (*):</label>
                    <input type="number" step="0.1" min="0" max="10" id="attendanceScore" name="attendanceScore" placeholder="0 - 10 (Ví dụ: 8)" value="${attendanceScore}" required>
                </div>

                <div class="form-group">
                    <label for="midtermScore">Điểm Giữa Kỳ (30%) (*):</label>
                    <input type="number" step="0.1" min="0" max="10" id="midtermScore" name="midtermScore" placeholder="0 - 10 (Ví dụ: 7)" value="${midtermScore}" required>
                </div>

                <div class="form-group full-width">
                    <label for="finalScore">Điểm Cuối Kỳ (60%) (*):</label>
                    <input type="number" step="0.1" min="0" max="10" id="finalScore" name="finalScore" placeholder="0 - 10 (Ví dụ: 9)" value="${finalScore}" required>
                </div>
            </div>

            <div class="btn-group">
                <button type="submit" class="btn btn-primary">🧮 Tính Điểm & Xếp Loại</button>
                <button type="reset" class="btn btn-secondary">🔄 Nhập Lại Form</button>
                <a href="${pageContext.request.contextPath}/student?action=clear" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa tất cả danh sách không?');">🗑️ Xóa Danh Sách</a>
            </div>
        </form>
    </div>

    <!-- Bảng Hiển thị kết quả -->
    <div class="card">
        <h2 class="card-title">2. Hiển Thị Bảng Kết Quả</h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>STT</th>
                    <th>Mã SV</th>
                    <th>Họ và Tên</th>
                    <th>Chuyên cần (10%)</th>
                    <th>Giữa kỳ (30%)</th>
                    <th>Cuối kỳ (60%)</th>
                    <th>Điểm tổng kết</th>
                    <th>Xếp loại</th>
                    <th>Định dạng hiển thị</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty students}">
                        <c:forEach var="st" items="${students}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td><strong>${st.studentId}</strong></td>
                                <td>${st.fullName}</td>
                                <td>${st.attendanceScore}</td>
                                <td>${st.midtermScore}</td>
                                <td>${st.finalScore}</td>
                                <td><strong>${st.totalScoreFormatted}</strong></td>
                                <td><span class="badge badge-${st.gradeLetter}">${st.gradeLetter}</span></td>
                                <td><code>${st.summaryString}</code></td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9" style="text-align: center; color: #777;">Chưa có dữ liệu sinh viên nào. Vui lòng nhập dữ liệu ở trên!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
