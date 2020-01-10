import 'package:memeory/model/scrolling_axis.dart';
import 'package:memeory/util/consts/consts.dart';

import '../storage.dart';

Future<ScrollingAxis> getPreferredScrollingAxis() async {
  final value = await get(SELECTED_SCROLLING_AXIS_KEY);
  return scrollingAxisFromString(value) ?? ScrollingAxis.VERTICAL;
}

setPreferredScrollingAxis(ScrollingAxis orientation) async {
  await put(SELECTED_SCROLLING_AXIS_KEY, orientation.toString().split('.').last);
}
