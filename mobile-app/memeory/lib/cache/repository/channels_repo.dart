import 'dart:convert';

import 'package:memeory/util/consts/consts.dart';
import 'package:quiver/strings.dart';

import '../storage.dart';

Future<bool> getWatchAll() async {
  return await get(WATCH_ALL_CHANNELS_KEY) != false.toString();
}

Future<List> getSelectedChannels() async {
  final channels = await get(SELECTED_CHANNELS_KEY);
  return (isNotEmpty(channels)) ? json.decode(channels) : [];
}

setWatchAll(bool value) async {
  await put(WATCH_ALL_CHANNELS_KEY, value);
}

setChannels(List<dynamic> ids) async {
  final channels = ids ?? [];
  final res = json.encode(channels);
  await put(SELECTED_CHANNELS_KEY, res);
}

removeChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.remove(id);
  setChannels(ids);
}

removeAllChannels() async {
  await delete(SELECTED_CHANNELS_KEY);
}

addChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.add(id);
  setChannels(ids);
}
