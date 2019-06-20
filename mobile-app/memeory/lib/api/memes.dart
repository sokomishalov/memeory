import 'dart:convert';

import 'package:memeory/util/consts.dart';
import 'package:memeory/util/env.dart';
import 'package:memeory/util/http.dart';

Future<List> fetchMemes(page) async {
  final url = '${env.backendUrl}/memes/page/$page/$MEMES_COUNT_ON_PAGE';
  final response = await http.get(url);
  return json.decode(utf8.decode(response.bodyBytes));
}
