# Allo Shell test

**Safety rule: this test must never send a request to a real shop, operator, or customer. Never press any button that sends, submits, confirms, pays, logs in, signs up, books, posts a review, applies as a partner, applies to join a shop, or sends a consultation request. Stop at the screen containing that final button, take a screenshot, and report what you reached.**

Follow `AGENT_BRIEF.md` exactly: use only its listed tools, take and inspect a screenshot after every action, keep its screenshot numbering, and write the final `report.md` and `findings.json` in the exact location and format it specifies.

Allo Shell contains **All.Lo**, a web service for travellers to find and order from shops in Busan. Test translation, layout, navigation, changing languages, and viewing maps. Play the persona assigned in `personas.json` honestly.

## Never submit anything

- Do not press these final buttons: ko `주문하기`, `등록`, `신청하기`; zh-TW `送出訂單`, `送出`, `申請`; zh-CN `提交订单`, `提交`, `申请`; en `Place order`, `Post review`, `Apply`; ja `注文する`, `送信`, `申込`; vi `Gửi đơn hàng`, `Đăng`, `Đăng ký`.
- The list is not exhaustive. If a button means send, submit, confirm, pay, log in, or sign up in any language, do not press it. If unsure, do not press it.
- Enter test-only text if a form needs input: name `Test`, memo `ZZ-SIM`. Never enter a real phone number, e-mail address, card, or street address.
- If a Google, Kakao, or LINE login screen opens, close it and go back.
- The shell blocks write requests. If an error such as `Blocked by Allo Shell test guard` appears, that is expected and means a final action was pressed. Do not press it again; report the screen and the button name.

## Stores to cover (start on the store list, then open each store's page)

The first screen is the list of stores open for orders. Visit these four store pages (match by name; names may appear in Korean or in your language): `깃뜰 홍대본점` (Git-tteul Hongdae), `옴쎄` (Omsse), `교촌치킨 신주례점` (Kyochon Sinjurye), `하르당 수영` (Hardang Suyeong). On each store page actually tap, in this order, and report what happens and what you see: **menu viewing** (open the menu, scroll, open an item), **language change** (to another language and back), **map** (`지도에서 보기` / View on map; Google Maps opens inside the app), **share** (any share/copy-link button), **photo save** (long-press a store or menu photo, or any save-photo button). Share and photo save may not work inside this test shell: report exactly what you tried and what the screen did. Spread your actions so that all four stores are visited at least once within your action budget; reach the order form on one or two of them only.

## Mission

As a traveller visiting Busan, find a suitable shop, open its shop page, inspect its menu, and add an item to the cart. Continue through the order flow only until the screen immediately before the final send button, then stop there.

Also complete these checks:

1. On the first screen, decide whether it is clear what the service is for.
2. Find shops using search and/or the list.
3. On a shop page, view the menu and add an item.
4. Reach the order form, but stop immediately before its final send button.
5. Change the screen language to another language, then change it back. Check for mixed or truncated text and whether the current state remains after each switch.
6. Use the shop's `지도에서 보기` (View on map) action to open Google Maps in the WebView.

## What the report must include

In addition to every item required by `AGENT_BRIEF.md`, report mixed languages, clipped text, shifted layouts, awkward translations, text left in another language after switching, whether View on map worked, and every dead end. Include the final button name and screenshot when you stop before it.
