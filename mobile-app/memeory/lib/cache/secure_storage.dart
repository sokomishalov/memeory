import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:localstorage/localstorage.dart';
import 'package:memeory/util/os.dart';

final _desktopStorage = LocalStorage('secure_storage');
final _mobileSecureStorage = FlutterSecureStorage();

putSecure(String key, dynamic value) async {
  return isMobile()
      ? _mobileSecureStorage.write(key: key, value: value.toString())
      : await _desktopStorage.setItem(key, value);
}

getSecure(String key) async {
  return isMobile()
      ? _mobileSecureStorage.read(key: key)
      : await _desktopStorage.getItem(key);
}

// ignore: unused_element
deleteSecure(String key) async {
  return isMobile()
      ? _mobileSecureStorage.delete(key: key)
      : await _desktopStorage.deleteItem(key);
}
