import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/util/consts/consts.dart';

import '../storage.dart';

Future<ScrollingAxis> getPreferredOrientation() async {
  final value = await get(SELECTED_ORIENTATION_KEY);
  return orientationFromString(value) ?? ScrollingAxis.VERTICAL;
}

setPreferredOrientation(ScrollingAxis orientation) async {
  await put(SELECTED_ORIENTATION_KEY, orientation.toString().split('.').last);
}
