import 'package:memeory/model/channel.dart';
import 'package:memeory/model/topic.dart';

class ProvidersFetchAction {}

class ProvidersReceivedAction {
  List<String> providers;

  ProvidersReceivedAction(this.providers);
}

class TopicsFetchAction {}

class TopicsReceivedAction {
  List<Topic> topics;

  TopicsReceivedAction(this.topics);
}

class ChannelsFetchAction {}

class ChannelsReceivedAction {
  List<Channel> channels;

  ChannelsReceivedAction(this.channels);
}
