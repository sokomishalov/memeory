import 'package:chewie/chewie.dart';
import 'package:flutter/material.dart';
import 'package:memeory/api/profile.dart';
import 'package:memeory/cache/repository/orientations_repo.dart';
import 'package:memeory/components/containers/conditional_widget.dart';
import 'package:memeory/model/orientation.dart';
import 'package:memeory/theme/dark.dart';
import 'package:memeory/theme/light.dart';
import 'package:memeory/theme/theme.dart';
import 'package:memeory/util/consts.dart';
import 'package:video_player/video_player.dart';

class OrientationPreferences extends StatefulWidget {
  @override
  _OrientationPreferencesState createState() => _OrientationPreferencesState();
}

class _OrientationPreferencesState extends State<OrientationPreferences> {
  static const aspectRatio = 2 / 3;

  VideoPlayerController _verticalVideoController;
  VideoPlayerController _horizontalVideoController;

  ChewieController _verticalChewieController;
  ChewieController _horizontalChewieController;

  MemesOrientation _preferredOrientation;

  @override
  void initState() {
    _verticalVideoController = VideoPlayerController.asset(
      VERTICAL_ORIENTATION_ASSET,
    );

    _verticalChewieController = ChewieController(
      videoPlayerController: _verticalVideoController,
      aspectRatio: aspectRatio,
      showControls: false,
      autoInitialize: true,
      looping: true,
    );

    _horizontalVideoController = VideoPlayerController.asset(
      HORIZONTAL_ORIENTATION_ASSET,
    );

    _horizontalChewieController = ChewieController(
      videoPlayerController: _horizontalVideoController,
      aspectRatio: aspectRatio,
      showControls: false,
      autoInitialize: true,
      looping: true,
    );

    super.initState();

    getPreferredOrientation().then((o) {
      if (o == MemesOrientation.HORIZONTAL) {
        _horizontalChewieController.play();
      } else {
        _verticalChewieController.play();
      }
      setState(() {
        _preferredOrientation = o;
      });
    });
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
      child: Center(
        child: SingleChildScrollView(
          child: Row(
            children: [
              videoWrapper(MemesOrientation.VERTICAL),
              videoWrapper(MemesOrientation.HORIZONTAL),
            ],
          ),
        ),
      ),
    );
  }

  Future<void> setOrientation(MemesOrientation orientation) async {
    await setPreferredOrientation(orientation);

    var newOrientation = await getPreferredOrientation();

    setState(() {
      _preferredOrientation = newOrientation;
    });

    if (orientation == MemesOrientation.HORIZONTAL) {
      await _horizontalChewieController.play();
      await _verticalChewieController.pause();
    } else {
      await _verticalChewieController.play();
      await _horizontalChewieController.pause();
    }

    await saveProfile();
  }

  Widget videoWrapper(MemesOrientation orientation) {
    final width = (MediaQuery.of(context).size.width / 2);
    final height = width / aspectRatio;

    return GestureDetector(
      onTap: () async => await setOrientation(orientation),
      child: Stack(
        children: [
          Container(
            padding: EdgeInsets.all(10),
            width: width,
            height: height,
            child: Chewie(
              controller: orientation == MemesOrientation.HORIZONTAL
                  ? _horizontalChewieController
                  : _verticalChewieController,
            ),
          ),
          ConditionalWidget(
            condition: _preferredOrientation == orientation,
            child: Container(
              width: width,
              height: height,
              child: Center(
                child: Icon(
                  Icons.done,
                  size: 40,
                  color: dependingOnThemeChoice(
                    context: context,
                    light: ORIENTATION_MARK_COLOR_LIGHT,
                    dark: ORIENTATION_MARK_COLOR_DARK,
                  ),
                ),
              ),
            ),
          ),
          ConditionalWidget(
            condition: _preferredOrientation == orientation,
            child: Container(
              width: width,
              height: height,
              color: dependingOnThemeChoice(
                context: context,
                light: ORIENTATION_CHOICE_COLOR_LIGHT,
                dark: ORIENTATION_CHOICE_COLOR_DARK,
              ),
            ),
          )
        ],
      ),
    );
  }
}
