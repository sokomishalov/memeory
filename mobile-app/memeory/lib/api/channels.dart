import 'dart:convert';

import 'package:memeory/model/channel.dart';
import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List<Channel>> fetchChannels() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}channels/list';
  final response = await http.get(url);
  var decodedData = json.decode(utf8.decode(response.bodyBytes));
  return List.of(decodedData).map((it) => Channel.fromJson(it)).toList();
}

String getChannelLogoUrl(String channelId) {
  final baseUrl = getBackendUrl();
  return '${baseUrl}channels/logo/$channelId';
}
