import 'dart:convert';
import 'dart:io';

import 'package:memeory/model/meme.dart';
import 'package:memeory/model/memes_page_request.dart';
import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/env/env.dart';
import 'package:memeory/util/http/http.dart';

Future<List<Meme>> fetchMemes(MemesPageRequest request) async {
  final headers = const <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE
  };

  final body = json.encode(request.toJson());

  final url = getBackendUrl(uri: "memes/page");
  final response = await http.post(url, headers: headers, body: body);

  var decodedData = json.decode(utf8.decode(response.bodyBytes));
  return List.of(decodedData).map((it) => Meme.fromJson(it)).toList();
}

String getMemeShareUrl(String memeId) {
  var baseFrontendUrl = getFrontendUrl();
  return "${baseFrontendUrl}memes/single/$memeId";
}
