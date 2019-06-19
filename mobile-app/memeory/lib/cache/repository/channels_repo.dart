import 'dart:convert';

import 'package:memeory/util/consts.dart';

import '../storage.dart';

getSelectedChannels() async {
  final channels = await get(SELECTED_CHANNELS_KEY);
  return channels != null ? json.decode(channels) : [];
}

removeChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.remove(id);
  final res = json.encode(ids);
  await put(SELECTED_CHANNELS_KEY, res);
}

addChannel(String id) async {
  final ids = await getSelectedChannels();
  ids.add(id);
  final res = json.encode(ids);
  await put(SELECTED_CHANNELS_KEY, res);
}
