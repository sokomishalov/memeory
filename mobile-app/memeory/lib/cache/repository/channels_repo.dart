import 'dart:convert';

import 'package:memeory/util/consts.dart';
import 'package:quiver/strings.dart';

import '../storage.dart';

getWatchAll() async {
  return await get(WATCH_ALL_CHANNELS_KEY) != false.toString();
}

setWatchAll(bool value) async {
  await put(WATCH_ALL_CHANNELS_KEY, value);
}

getSelectedChannels() async {
  final channels = await get(SELECTED_CHANNELS_KEY);
  return isNotEmpty(channels) ? json.decode(channels) : [];
}

removeChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.remove(id);
  final res = json.encode(ids);
  await put(SELECTED_CHANNELS_KEY, res);
}

removeAllChannels() async {
  await delete(SELECTED_CHANNELS_KEY);
}

addChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.add(id);
  final res = json.encode(ids);
  await put(SELECTED_CHANNELS_KEY, res);
}
