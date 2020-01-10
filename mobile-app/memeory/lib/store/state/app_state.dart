import 'package:memeory/model/channel.dart';
import 'package:memeory/model/topic.dart';

class AppState {
  List<String> providers;
  List<Topic> topics;
  List<Channel> channels;

  AppState(
    this.providers,
    this.topics,
    this.channels,
  );

  factory AppState.initial() {
    return AppState(
      List.unmodifiable([]),
      List.unmodifiable([]),
      List.unmodifiable([]),
    );
  }
}
