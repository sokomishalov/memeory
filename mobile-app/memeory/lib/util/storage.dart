import 'dart:convert';

import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:localstorage/localstorage.dart';
import 'package:memeory/util/consts.dart';

import 'os.dart';

final mobileStorage = new FlutterSecureStorage();
final desktopStorage = new LocalStorage('memeory');

// FIXME revert
//isFirstAppVisit() async => await _get(APP_VISIT_DATETIME_KEY) == null;
isFirstAppVisit() async => true;

setAppVisitDatetime() async {
  final currentTimestamp = new DateTime.now().millisecondsSinceEpoch / 1000;
  return await _put(APP_VISIT_DATETIME_KEY, currentTimestamp);
}

getSelectedChannels() async {
  final channels = await _get(SELECTED_CHANNELS_KEY);
  return channels != null ? json.decode(channels) : [];
}

removeChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.remove(id);
  final res = json.encode(ids);
  await _put(SELECTED_CHANNELS_KEY, res);
}

addChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.add(id);
  final res = json.encode(ids);
  await _put(SELECTED_CHANNELS_KEY, res);
}

_put(String key, dynamic value) async {
  return isMobile()
      ? await mobileStorage.write(key: key, value: value.toString())
      : await desktopStorage.setItem(key, value);
}

_get(String key) async {
  return isMobile()
      ? await mobileStorage.read(key: key)
      : await desktopStorage.getItem(key);
}

// ignore: unused_element
_delete(String key) async {
  return isMobile()
      ? await mobileStorage.delete(key: key)
      : await desktopStorage.deleteItem(key);
}
