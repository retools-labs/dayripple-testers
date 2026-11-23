# 올로(All.Lo) 시험 실행

기본 워크플로 값은 하루결용이므로 바꾸지 않습니다. 올로 시험은 실행 입력값으로만 덮어씁니다.

```sh
gh workflow run agent-testers.yml -R retools-labs/dayripple-testers -f source_repo= -f ref= -f app_dir=allo-shell -f app_id=kr.retools.alloshell -f framework=native -f android_build="chmod +x gradlew && ./gradlew assembleDebug --build-cache" -f apk_path=app/build/outputs/apk/debug/app-debug.apk -f android_personas=allo-zhtw,allo-ja,allo-zhcn,allo-en,allo-vi -f brief=briefs/allo.md -f model=haiku
```

시작 주소를 바꾸려면 빌드 명령에 `-PalloUrl=https://…`를 추가합니다. 예를 들어 `./gradlew assembleDebug -PalloUrl=https://preview.example.com/`처럼 실행합니다.

결과는 워크플로 산출물을 받은 뒤 `collect.sh`로 복호화합니다.

안전장치: 껍데기는 모든 쓰기 요청을 차단합니다. 단, 올로의 배달비 조회 `POST /api/guide/delivery-quote`만 허용합니다. `tel:`, `sms:`, `intent:`, `line:`, `kakaotalk:`, `market:` 같은 외부 스킴도 열지 않습니다.

되돌리기: `allo-shell/`, `briefs/allo.md`, `ALLO_RUN.md`를 삭제하고 `personas.json`에서 `allo-` 페르소나 5개를 삭제합니다.
