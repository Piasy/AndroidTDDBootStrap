#!/usr/bin/python
# -*- coding: utf-8 -*-
# Thanks for [hujin1860@gmail.com]
import sys
import xml.etree.ElementTree as ET
package = '{http://schemas.android.com/apk/res/android}'
manifest = ET.parse(sys.argv[1])
versionName = sys.argv[3]
versionCode = sys.argv[4]
minSdk = sys.argv[5]
targetSdk = sys.argv[6]
debuggable = sys.argv[7]
root = manifest.getroot()
if '{0}versionName'.format(package) not in root.attrib:
	root.attrib['{0}versionName'.format(package)] = versionName
if '{0}versionCode'.format(package) not in root.attrib:
	root.attrib['{0}versionCode'.format(package)] = versionCode
if len(root.findall("./uses-sdk")) == 0:
	ET.SubElement(root, 'uses-sdk', {
		'{0}targetSdkVersion'.format(package): targetSdk,
		'{0}minSdkVersion'.format(package): minSdk,
	})
application = root.find("./application")
if '{0}debuggable'.format(package) not in application.attrib:
	application.attrib['{0}debuggable'.format(package)] = debuggable
manifest.write(sys.argv[2])
