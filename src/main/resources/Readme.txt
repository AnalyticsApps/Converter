***********************************************************************************
***********************************************************************************

*************     TOOL TO SETUP FIXED FILE FORMAT CONVERTER        ****************

***********************************************************************************
***********************************************************************************

Author : Nisanth Simon


Download the RPM from https://github.com/AnalyticsApps/Converter/raw/master/RPM/FileConverter-1.0-1.noarch.rpm



Install the RPM
===============

[root@testeserver ~]#
[root@testeserver ~]# rpm -ivh FileConverter-1.0-1.noarch.rpm
Preparing...                          ################################# [100%]
Updating / installing...
   1:FileConverter-1.0-1              ################################# [100%]
[root@testeserver ~]#


After installing the RPM, 
	1) The scripts will be available under /opt/FileConverter/bin/
	2) Sample input files are under /opt/FileConverter/sample/
	3) The application logs will be in /opt/FileConverter/log/
	4) The log4j config will be in /opt/FileConverter/conf/


[root@testeserver ~]#
[root@testeserver ~]# cd /opt/FileConverter/
[root@testeserver FileConverter]#

[root@testeserver FileConverter]# ls
bin  conf  lib  log  Readme.txt  sample
[root@testeserver FileConverter]#


Sample Run
===========

[root@testeserver FileConverter]# bin/Converter


 Path to metadata file: /opt/FileConverter/sample/metadata.txt


 Path to data file: /opt/FileConverter/sample/input.txt


 Path to Out file: /opt/inputCSV.txt


 File conversion completed.


[root@testeserver FileConverter]#


Uninstall the RPM
=================

[root@testeserver FileConverter]# rpm -e FileConverter-1.0-1.noarch
Uninstalling the Cluster Setup completed
[root@testeserver FileConverter]#
