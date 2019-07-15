<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : main-model.xsl
    Created on : July 15, 2019, 5:40 PM
    Author     : NhanTT
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="main-model">
        <div class="column col-6 col-xs-12">
            <div class="card">
                <div class="card-image">
                    <img src="{image-src}" class="img-responsive" alt="{name}" title="{name}" width="100%"/>
                </div>
            </div>
        </div>
        <div class="column col-6 col-xs-12">
            <div class="card no-border">
                <div class="card-header">
                    <div class="card-title h3">
                        <xsl:value-of select="name"/>
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <tbody>
                                <tr>
                                    <td>Số tờ</td>
                                    <td><xsl:value-of select="num-of-sheets"/></td>
                                </tr>
                                <tr>
                                    <td>Độ khó</td>
                                    <td><xsl:value-of select="difficulty"/></td>
                                </tr>
                                <tr>
                                    <td>Thời gian</td>
                                    <td><xsl:value-of select="estimate-time"/></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="card-footer">
                        <a href="{link}" target="_blank" class="btn btn-lg btn-primary">Tải xuống</a>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
