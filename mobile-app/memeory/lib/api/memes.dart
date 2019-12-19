import 'dart:convert';
import 'dart:io';

import 'package:memeory/util/consts/consts.dart';
import 'package:memeory/util/firebase/firebase.dart';
import 'package:memeory/util/http/http.dart';

Future<List> fetchMemes(page) async {
  final baseUrl = getBackendUrl();
  final headers = const <String, String>{
    HttpHeaders.contentTypeHeader: APPLICATION_JSON_HEADER_VALUE
  };

  final url = '${baseUrl}memes/page/$page/$MEMES_COUNT_ON_THE_PAGE';
  final response = await http.get(url, headers: headers);

  return json.decode(utf8.decode(response.bodyBytes));
}
