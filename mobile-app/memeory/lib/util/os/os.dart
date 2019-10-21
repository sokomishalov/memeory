import 'dart:io' show Platform;

bool isAndroid() => Platform.isAndroid;

bool isIos() => Platform.isIOS;

bool isMobile() => Platform.isIOS || Platform.isAndroid;

bool isDesktop() => !isMobile();
