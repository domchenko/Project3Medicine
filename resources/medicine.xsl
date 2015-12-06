<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:p="http://www.example.net/test"
  exclude-result-prefixes="xs p"
  version="2.0">
  <xsl:template match="/">
    <html>
      <body>
        <table style="border: 1px solid black;" cellspace="0">
          <tr>
            <td>Id</td>
            <td>Name</td>
            <td>Pharm</td>
            <td>Group</td>
            <td>Analogs</td>
            <td>Versions</td>
          </tr>
          <xsl:for-each select="drugs/medicine">
            <tr style="height: 2pt;">
              <td colspan="6" style="border-bottom: 1.5px solid black;"></td>
            </tr>
            <tr>
              <td><xsl:value-of select="@id"/></td>
              <td><xsl:value-of select="name"/></td>
              <td><xsl:value-of select="pharm"/></td>
              <td><xsl:value-of select="group"/></td>
              <td>
                <ul>
                  <xsl:for-each select="analogs/analog">
                  <li><xsl:value-of select="."/></li>
                  </xsl:for-each>
                </ul>
              </td>
              <td>              
                <xsl:for-each select="versions/version">
                  <p><b><xsl:value-of select="@type"/></b></p>
                  <p>
                    <xsl:text>Certificate #</xsl:text>
                    <xsl:value-of select="certificate/@number"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="certificate/@dateFrom"/>
                    <xsl:text>-</xsl:text>
                    <xsl:value-of select="certificate/@dateTo"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="certificate/organization"/>
                  </p>
                  <p>
                    <xsl:text>Package: </xsl:text>
                    <xsl:value-of select="package/p:name"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="package/p:amount/."/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="package/p:amount/@measure"/>
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="package/p:price/."/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="package/p:price/@currency"/>
                  </p>
                  <p>
                    <xsl:text>Dosage: </xsl:text>
                    <xsl:value-of select="dosage/number"/>
                    <xsl:text> portion(s) </xsl:text>
                    <xsl:value-of select="dosage/periodicity"/>
                    <xsl:text> time(s)</xsl:text>
                  </p>
                </xsl:for-each>
              </td>
            </tr>
          </xsl:for-each>        
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>