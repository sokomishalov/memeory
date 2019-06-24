import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/model/orientation.dart';
import 'package:video_player/video_player.dart';

class OrientationPreferences extends StatefulWidget {
  @override
  _OrientationPreferencesState createState() => _OrientationPreferencesState();
}

class _OrientationPreferencesState extends State<OrientationPreferences> {
  VideoPlayerController _verticalVideoController;
  VideoPlayerController _horizontalVideoController;

  ChewieController _verticalChewieController;
  ChewieController _horizontalChewieController;

  MemesOrientation _preferredOrientation;

  @override
  void initState() {
    getPreferredOrientation().then((o) {
      setState(() {
        _preferredOrientation = o;
      });
    });

    _verticalVideoController = VideoPlayerController.asset(
      'assets/orientation/vertical.mp4',
    );

    _verticalChewieController = ChewieController(
      videoPlayerController: _verticalVideoController,
      showControls: false,
      aspectRatio: 4 / 4,
      autoPlay: true,
      looping: true,
    );

    _horizontalVideoController = VideoPlayerController.asset(
      'assets/orientation/horizontal.mp4',
    );

    _horizontalChewieController = ChewieController(
      videoPlayerController: _horizontalVideoController,
      showControls: false,
      aspectRatio: 4 / 4,
      autoPlay: true,
      looping: true,
    );

    super.initState();
  }

  @override
  void dispose() {
    _verticalVideoController?.dispose();
    _horizontalVideoController?.dispose();

    _verticalChewieController?.dispose();
    _horizontalChewieController?.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Expanded(
      child: ListView(
        shrinkWrap: true,
        children: [
          videoWrapper(MemesOrientation.VERTICAL),
          videoWrapper(MemesOrientation.HORIZONTAL),
        ],
      ),
    );
  }

  Widget videoWrapper(orientation) {
    return GestureDetector(
      onTap: () async {
        await setPreferredOrientation(orientation);

        var newOrientation = await getPreferredOrientation();

        setState(() {
          _preferredOrientation = newOrientation;
        });
      },
      child: Container(
        padding: EdgeInsets.all(20),
        color: _preferredOrientation == orientation
            ? Colors.greenAccent.shade200
            : Colors.transparent,
        child: Chewie(
          controller: orientation == MemesOrientation.HORIZONTAL
              ? _horizontalChewieController
              : _verticalChewieController,
        ),
      ),
    );
  }
}
