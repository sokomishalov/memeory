import 'dart:convert';
import 'dart:io';

import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List> fetchMemes({
  int pageNumber,
  int pageSize = MEMES_COUNT_ON_THE_PAGE,
  String providerId = null,
  String topicId = null,
  String channelId = null,
}) async {
  final baseUrl = getBackendUrl();
  final headers = const <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE
  };

  final body = json.encode({
    "pageNumber": pageNumber,
    "pageSize": MEMES_COUNT_ON_THE_PAGE,
    "providerId": providerId,
    "topicId": topicId,
    "channelId": channelId
  });

  final url = '${baseUrl}memes/page';
  final response = await http.post(url, headers: headers, body: body);

  return json.decode(utf8.decode(response.bodyBytes));
}

String getMemeShareUrl(String memeId) {
  var baseFrontendUrl = getFrontendUrl();
  return "${baseFrontendUrl}memes/single/$memeId";
}
