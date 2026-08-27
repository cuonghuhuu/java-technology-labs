<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Kết Quả Tính Điểm - javalabTuyen</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="container">

    <div class="header">
        <h1>Kết Quả Tính Điểm & Xếp Loại Chi Tiết</h1>
        <p>Hệ thống xử lý bài tập Công nghệ Java - javalabTuyen</p>
    </div>

    <!-- Thông báo kết quả -->
    <c:if test="${not empty currentStudent}">
        <div class="card">
            <h2 class="card-title">🎉 Chi Tiết Xử Lý Cho Sinh Viên: ${currentStudent.fullName}</h2>

            <div class="result-box">
                <div>Mã SV: <strong>${currentStudent.studentId}</strong> | Họ tên: <strong>${currentStudent.fullName}</strong></div>
                <div class="summary">
                    Kết Quả: ${currentStudent.summaryString}
                </div>
                <div>Xếp Loại: <span class="badge badge-${currentStudent.gradeLetter}" style="font-size: 16px; padding: 6px 14px;">Hạng ${currentStudent.gradeLetter}</span></div>
            </div>

            <table style="margin-bottom: 20px;">
                <tr>
                    <th>Chức năng</th>
                    <th>Chi tiết xử lý theo công thức</th>
                    <th>Kết quả ví dụ thực tế</th>
                </tr>
                <tr>
                    <td><strong>1. Nhập dữ liệu</strong></td>
                    <td>Nhập Mã SV, Họ tên, Điểm chuyên cần, Điểm giữa kỳ, Điểm cuối kỳ</td>
                    <td><code>${currentStudent.studentId}, ${currentStudent.fullName}, ${currentStudent.attendanceScore}, ${currentStudent.midtermScore}, ${currentStudent.finalScore}</code></td>
                </tr>
                <tr>
                    <td><strong>2. Tính điểm tổng kết</strong></td>
                    <td>Điểm TK = Chuyên cần × 10% + Giữa kỳ × 30% + Cuối kỳ × 60%</td>
                    <td><code>${currentStudent.attendanceScore} × 0.1 + ${currentStudent.midtermScore} × 0.3 + ${currentStudent.finalScore} × 0.6 = ${currentStudent.totalScoreFormatted}</code></td>
                </tr>
                <tr>
                    <td><strong>3. Xếp loại</strong></td>
                    <td>A: ≥ 8.5 | B: ≥ 7.0 | C: ≥ 5.5 | D: ≥ 4.0 | F: &lt; 4.0</td>
                    <td><code>${currentStudent.totalScoreFormatted} → Hạng ${currentStudent.gradeLetter}</code></td>
                </tr>
                <tr>
                    <td><strong>4. Hiển thị kết quả</strong></td>
                    <td>In bảng thông tin sinh viên, điểm tổng kết và xếp loại</td>
                    <td><code>${currentStudent.summaryString}</code></td>
                </tr>
            </table>

            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/student" class="btn btn-primary">➕ Nhập Thêm Sinh Viên Mới</a>
            </div>
        </div>
    </c:if>

    <!-- Bảng tổng hợp danh sách tất cả các sinh viên -->
    <div class="card">
        <h2 class="card-title">📋 Bảng Tổng Hợp Danh Sách Sinh Viên</h2>
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
                    <th>Tổng kết</th>
                    <th>Xếp loại</th>
                    <th>Chuỗi hiển thị kết quả</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="st" items="${students}" varStatus="loop">
                    <tr style="${st.studentId eq currentStudent.studentId ? 'background-color: #e8f4f8; font-weight: bold;' : ''}">
                        <td>${loop.index + 1}</td>
                        <td>${st.studentId}</td>
                        <td>${st.fullName}</td>
                        <td>${st.attendanceScore}</td>
                        <td>${st.midtermScore}</td>
                        <td>${st.finalScore}</td>
                        <td>${st.totalScoreFormatted}</td>
                        <td><span class="badge badge-${st.gradeLetter}">${st.gradeLetter}</span></td>
                        <td><code>${st.summaryString}</code></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
