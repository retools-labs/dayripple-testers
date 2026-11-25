# 올로(All.Lo) 시험 실행

기본 워크플로 값은 하루결용이므로 바꾸지 않습니다. 올로 시험은 실행 입력값으로만 덮어씁니다. 🔴 `source_repo` 를 비우면 기본값(하루결 저장소)으로 되돌아가 `allo-shell` 을 못 찾고 빌드가 실패합니다(11/25 실패 사례). 반드시 `source_repo=retools-labs/dayripple-testers -f ref=main` 을 줍니다.

```sh
gh workflow run agent-testers.yml -R retools-labs/dayripple-testers -f source_repo=retools-labs/dayripple-testers -f ref=main -f app_dir=allo-shell -f app_id=kr.retools.alloshell -f framework=native -f android_build="chmod +x gradlew && ./gradlew assembleDebug --build-cache" -f apk_path=app/build/outputs/apk/debug/app-debug.apk -f android_personas=allo-zhtw,allo-ja,allo-zhcn,allo-en,allo-vi -f brief=briefs/allo.md -f model=haiku
```

시작 주소를 바꾸려면 빌드 명령에 `-PalloUrl=https://…`를 추가합니다. 예를 들어 `./gradlew assembleDebug -PalloUrl=https://preview.example.com/`처럼 실행합니다.

결과는 워크플로 산출물을 받은 뒤 `collect.sh`로 복호화합니다.

안전장치: 껍데기는 모든 쓰기 요청을 차단합니다. 단, 올로의 배달비 조회 `POST /api/guide/delivery-quote`만 허용합니다. `tel:`, `sms:`, `intent:`, `line:`, `kakaotalk:`, `market:` 같은 외부 스킴도 열지 않습니다.

되돌리기: `allo-shell/`, `briefs/allo.md`, `ALLO_RUN.md`를 삭제하고 `personas.json`에서 `allo-` 페르소나 5개를 삭제합니다.

## 기기 로그 (11/25 추가)

시험이 끝나면 결과 묶음의 `results/<페르소나>/device_logs/` 에 `logcat_all.txt`, `logcat_filtered.txt`, `exit_info.txt`, `meminfo_end.txt`, `top_activity_end.txt`, `top_activity_timeline.txt`(10초 간격 최상위 화면)가 들어갑니다. 껍데기는 `AlloShell` 태그로 화면 생명주기·메모리 신호·WebView 렌더러 종료를 남깁니다. 되돌리기: `scripts/android/run_agent.sh` 의 `DEVICE_LOG_DIR` 블록 두 곳과 껍데기의 `AlloShell` 로그를 삭제합니다.

## 대조 실험 (첫 탭 이탈이 올로 탓인지 껍데기 탓인지)

올로가 아닌 단추 하나짜리 정적 페이지로 같은 껍데기·같은 에뮬레이터를 시험합니다. 여기서도 첫 탭 이탈이 나면 껍데기 문제로 확정됩니다.

```sh
gh workflow run agent-testers.yml -R retools-labs/dayripple-testers -f source_repo=retools-labs/dayripple-testers -f ref=main -f app_dir=allo-shell -f app_id=kr.retools.alloshell -f framework=native -f android_build="chmod +x gradlew && ./gradlew assembleDebug --build-cache -PalloUrl=file:///android_asset/control.html" -f apk_path=app/build/outputs/apk/debug/app-debug.apk -f android_personas=allo-vi,allo-zhcn -f brief=briefs/allo_control.md -f model=haiku
```

되돌리기: `allo-shell/app/src/main/assets/control.html`·`briefs/allo_control.md`를 삭제하고, 이 문서의 대조 실험 절과 껍데기의 `control.html` 예외 두 곳(`MainActivity`·`RequestGuard`)을 삭제합니다.
