# BarkAlert

Service for monitoring audio, and alerts if noise level is too high.

1. Record short .wav audio segments.
2. Convert to .wav to png using ffmpeg.
3. Analyse png to check audio level.
4. Upload audio and image to home NAS.
5. If too loud - notify via discord message, containing image and audio.
