<%@ page import="servlet.Coordinate" %>
<%@ page import="java.util.Vector" %>
<%--
    Created by IntelliJ IDEA.
    User: Банан
    Date: 15.10.2019
    Time: 22:20
    To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lab 2</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<table class="wrapper">
    <tr class="header">
        <td class="student-name">Лукьяненко Никита Р3210</td>
        <td class="variant">Вариант 210008</td>
    </tr>
    <tr class="content">
        <td colspan="2">
            <h1>Лабораторная работа №1</h1>
            <p>Укажите параметры X, Y и R</p>
            <div class="content-wrapper">
                <div class="graph">
                    <canvas id="plot" width="270" height="270"></canvas>
                </div>

                <form class="selection">
                    <div class="x-select">
                        <label class="selection-label" for="X-select">Выберите X
                            <span class="warning" data-validate="Выберите X"></span>
                        </label>
                        <select name="x" id="X-select">
                            <option value="-4">-4</option>
                            <option value="-3">-3</option>
                            <option value="-2">-2</option>
                            <option value="-1">-1</option>
                            <option value="0" selected="selected">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                        </select>
                    </div>
                    <div class="y-select">
                        <label class="selection-label" for="Y-select">Выберите Y
                            <span class="warning" data-validate="Y - значение в диапазоне [-5; 5]"></span>
                        </label>
                        <input id="Y-select" type="text" placeholder="-5...5" name="y" autocomplete="off">
                    </div>
                    <div class="r-select">
                        <label class="selection-label">Выберите R
                            <span class="warning" data-validate="Выберите 1 значение"></span>
                        </label>
                        <input class="inp-cbx" id="r1" type="checkbox" name="r" value="1">
                        <label class="cbx" for="r1">
                                <span>
                                    <svg width="14px" height="10px" viewbox="0 0 12 10">
                                        <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
                                    </svg>
                                </span>
                            <span>1</span>
                        </label>
                        <input class="inp-cbx" id="r2" type="checkbox" name="r" value="1.5">
                        <label class="cbx" for="r2">
                                <span>
                                    <svg width="14px" height="10px" viewbox="0 0 12 10">
                                        <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
                                    </svg>
                                </span>
                            <span>1.5</span>
                        </label>
                        <input class="inp-cbx" id="r3" type="checkbox" name="r" value="2">
                        <label class="cbx" for="r3">
                                <span>
                                    <svg width="14px" height="10px" viewbox="0 0 12 10">
                                        <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
                                    </svg>
                                </span>
                            <span>2</span>
                        </label>
                        <input class="inp-cbx" id="r4" type="checkbox" name="r" value="2.5">
                        <label class="cbx" for="r4">
                                <span>
                                    <svg width="14px" height="10px" viewbox="0 0 12 10">
                                        <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
                                    </svg>
                                </span>
                            <span>2.5</span>
                        </label>
                        <input class="inp-cbx" id="r5" type="checkbox" name="r" value="3">
                        <label class="cbx" for="r5">
                                <span>
                                    <svg width="14px" height="10px" viewbox="0 0 12 10">
                                        <polyline points="1.5 6 4.5 9 10.5 1"></polyline>
                                    </svg>
                                </span>
                            <span>3</span>
                        </label>
                    </div>
                    <input class="btn-submit" type="submit" value="Отправить">
                    <div class="btn-submit clear-cookie">Стереть куки</div>
                </form>

                <table class="results">
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Попадание</th>
                        <th>Текущее время</th>
                        <th>Время работы скрипта, мкс</th>
                    </tr>
                    <% Object attribute = request.getSession().getServletContext().getAttribute("userData");
                        if (!(attribute == null || ((Vector<Coordinate>) attribute).size() == 0)) {
                            Vector<Coordinate> coordinates = (Vector<Coordinate>) attribute;
                            for (Coordinate element : coordinates) {
                    %>
                    <tr>
                        <td><%= element.getCastedX() %></td>
                        <td><%= element.getCastedY() %></td>
                        <td><%= element.getCastedR() %></td>
                        <%= element.getCorrectWords() %>
                        <td><%= element.getRequestTime() %></td>
                        <td><%= element.getExecutionTime() %></td>
                    </tr>
                    <% }} %>
                </table>
            </div>
        </td>
    </tr>
</table>
<div class="alert"></div>

<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
</body>
</html>