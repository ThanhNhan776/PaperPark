<%-- 
    Document   : home
    Created on : Jul 3, 2019, 1:55:12 AM
    Author     : NhanTT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/spectre.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/spectre-exp.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/spectre-icons.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/home.css" rel="stylesheet" type="text/css"/>
        <script src="js/home.js" type="text/javascript"></script>
        <title>PaperPark</title>
    </head>
    <body>
        <div class="app-logo">
            <a href="http://localhost:8084/PaperPark/">
                <img src="img/logo.jpg" alt="PaperPark" width="400"/>
            </a>
        </div>
        <h2 class="heading-title">Chọn mẫu mô hình giấy của bạn</h2>
        <div class="container">
            <div class="columns">
                <div class="column">
                    <h3 class="text-center">Gợi ý mô hình</h3>
                    <form method="#section-search-result" onsubmit="return false">
                        <div class="form-group">
                            <label class="form-label" for="selectDifficulty">Bạn muốn chọn mô hình có độ khó</label>
                            <select class="form-select bg-primary" id="selectDifficulty">
                                <option value="1">Cực dễ</option>
                                <option value="2">Dễ</option>
                                <option value="3">Trung bình</option>
                                <option value="4">Khó</option>
                                <option value="5">Cực khó</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Thời gian bạn có để làm mô hình</label>
                            <div class="input-group">
                                <input type="number" class="form-input text-right" min="0"
                                       id="makeTimeDay" onchange="handleTimePickerChangeDays()"/>
                                <span class="input-group-addon addon-custom">ngày</span>
                            </div>
                            <div class="input-group">
                                <input type="number" class="form-input text-right" min="0" max="24" step="0.1"
                                       id="makeTimeHoursPerDay" onchange="handleTimePickerChangeHoursPerDay()"/>
                                <span class="input-group-addon addon-custom">tiếng/ngày</span>
                            </div>
                            <div class="input-group">
                                <input type="number" class="form-input text-right bg-primary" min="0" step="0.1"
                                       id="makeTimeTotalHours" onchange="handleTimePickerChangeTotalHours()"/>
                                <span class="input-group-addon addon-custom bg-primary-emphasis">tiếng</span>
                            </div>
                        </div>
                        <div class="form-group text-center">
                            <button class="btn btn-primary" onclick="suggestModels()">Tìm mô hình</button>
                        </div>
                    </form>
                </div>

                <div class="divider-vert" data-content="Hoặc"></div>

                <div class="column">
                    <h3 class="text-center">Tìm mô hình</h3>
                    <form onsubmit="return false">
                        <div class="form-group">
                            <label class="form-label" for="modelName">Tên mô hình</label>
                            <input type="text" id="modelName" class="form-input"
                                   placeholder="Vd: thousand sunny"/>
                        </div>
                        <div class="form-group text-center">
                            <button class="btn btn-primary" onclick="searchModels()">Tìm mô hình</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div id="section-search-result">
            <div class="loading loading-lg"></div>
        </div>

<!--        <footer>
            <div class="footer text-center">
                <small>PaperPark Copyright © 2019 - Developed by NhanTT</small>
            </div>
        </footer>-->
    </body>
</html>
