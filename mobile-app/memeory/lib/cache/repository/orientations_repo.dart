import 'package:memeory/model/orientation.dart';
import 'package:memeory/util/consts.dart';

import '../storage.dart';

getPreferredOrientation() async {
  final value = await get(SELECTED_ORIENTATION_KEY);
  return orientationFromString(value) ?? MemesOrientation.VERTICAL;
}

setPreferredOrientation(MemesOrientation orientation) async {
  await put(SELECTED_ORIENTATION_KEY, orientation.toString().split('.').last);
}
