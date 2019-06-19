import 'package:localstorage/localstorage.dart';
import 'package:memeory/util/os.dart';
import 'package:shared_preferences/shared_preferences.dart';

final _desktopStorage = LocalStorage('storage');

Future _mobileStorage() async => await SharedPreferences.getInstance();

put(String key, dynamic value) async {
  return isMobile()
      ? (await _mobileStorage()).setString(key, value.toString())
      : await _desktopStorage.setItem(key, value);
}

get(String key) async {
  return isMobile()
      ? (await _mobileStorage()).getString(key)
      : await _desktopStorage.getItem(key);
}

// ignore: unused_element
delete(String key) async {
  return isMobile()
      ? (await _mobileStorage()).remove(key)
      : await _desktopStorage.deleteItem(key);
}
