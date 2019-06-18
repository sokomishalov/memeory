import 'dart:io' show Platform;

bool isMobile() => Platform.isIOS || Platform.isAndroid;

bool isDesktop() => !isMobile();
