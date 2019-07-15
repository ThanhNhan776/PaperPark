<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : model-list.xsl
    Created on : July 10, 2019, 2:08 PM
    Author     : NhanTT
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    
    <xsl:param name="pageSize" select="60"/>
    
    <xsl:template match="model-list">
        <xsl:for-each select="model[position() mod $pageSize = 1]">
            <div id="result-page-{position()}" class="columns hide">
                <xsl:apply-templates select="self::*|following-sibling::*[position() &lt; $pageSize]"/>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template match="model">
        <div class="column col-2 col-md-4 col-sm-6 col-xs-12">
            <div class="card">
                <div class="card-image">
                    <img src="{image-src}" class="img-responsive" alt="{name}" title="{name}" width="100%"/>    
                </div>
                <div class="card-header">
                    <div class="card-title h5">
                        <xsl:value-of select="name"/>
                    </div>
                    <div class="card-subtitle text-gray">Số tờ kit: <xsl:value-of select="num-of-sheets"/></div>
                </div>
                <div class="card-body">
                    <p>
                        Độ khó: <xsl:value-of select="difficulty"/>
                    </p>
                </div>
                <div class="card-footer">
                    <a href="http://localhost:8084/PaperPark/model.jsp?id={id}" target="_blank">Xem chi tiết</a>
                </div>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
