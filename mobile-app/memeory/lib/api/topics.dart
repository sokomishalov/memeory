import 'dart:convert';

import 'package:memeory/model/topic.dart';
import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List<Topic>> fetchTopics() async {
  final baseUrl = getBackendUrl();
  final url = '${baseUrl}topics/list';
  final response = await http.get(url);
  var decodedData = json.decode(utf8.decode(response.bodyBytes));
  return List.of(decodedData).map((it) => Topic.fromJson(it)).toList();
}
