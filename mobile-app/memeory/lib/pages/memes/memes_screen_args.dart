import 'package:memeory/model/scrolling_axis.dart';

class MemesScreenArgs {
  MemesScreenArgs({
    this.scrollingAxis = ScrollingAxis.VERTICAL,
    this.providerId = "",
    this.topicId = "",
    this.channelId = "",
    this.memeId = "",
  });

  final ScrollingAxis scrollingAxis;
  final String providerId;
  final String topicId;
  final String channelId;
  final String memeId;
}
