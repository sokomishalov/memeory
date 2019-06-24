import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoAttachment extends StatefulWidget {
  const VideoAttachment({Key key, this.url}) : super(key: key);

  final String url;

  @override
  _VideoAttachmentState createState() => _VideoAttachmentState();
}

class _VideoAttachmentState extends State<VideoAttachment> {
  VideoPlayerController _memeVideoController;
  ChewieController _memeChewieController;

  @override
  void initState() {
    _memeVideoController = VideoPlayerController.network(widget.url);

    _memeChewieController = ChewieController(
      videoPlayerController: _memeVideoController,
      showControls: false,
      aspectRatio: 4 / 4,
      autoPlay: false,
    );

    super.initState();
  }

  @override
  void dispose() {
    _memeVideoController?.dispose();
    _memeChewieController?.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Chewie(controller: _memeChewieController);
  }
}
