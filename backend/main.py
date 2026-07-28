from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
import json
import os
from datetime import datetime

app = FastAPI(title="炸弹卡片后台", version="1.0.0")

CARDS_FILE = "cards.json"

class CardCreate(BaseModel):
    content: str
    tags: List[str]
    source: Optional[str] = ""

class CardUpdate(BaseModel):
    content: Optional[str] = None
    tags: Optional[List[str]] = None
    source: Optional[str] = None

class Card(BaseModel):
    id: int
    content: str
    tags: List[str]
    source: str
    created_at: str
    updated_at: str

def load_cards():
    if not os.path.exists(CARDS_FILE):
        return []
    with open(CARDS_FILE, "r", encoding="utf-8") as f:
        return json.load(f)

def save_cards(cards):
    with open(CARDS_FILE, "w", encoding="utf-8") as f:
        json.dump(cards, f, ensure_ascii=False, indent=2)

def get_next_id(cards):
    if not cards:
        return 1
    return max(c["id"] for c in cards) + 1

@app.get("/")
def root():
    return {"message": "炸弹卡片后台服务", "version": "1.0.0"}

@app.get("/cards", response_model=List[Card])
def list_cards():
    return load_cards()

@app.get("/cards/{card_id}", response_model=Card)
def get_card(card_id: int):
    cards = load_cards()
    for card in cards:
        if card["id"] == card_id:
            return card
    raise HTTPException(status_code=404, detail="卡片不存在")

@app.post("/cards", response_model=Card)
def create_card(card: CardCreate):
    cards = load_cards()
    now = datetime.now().isoformat()
    new_card = {
        "id": get_next_id(cards),
        "content": card.content,
        "tags": card.tags[:2],  # Max 2 tags
        "source": card.source,
        "created_at": now,
        "updated_at": now
    }
    cards.append(new_card)
    save_cards(cards)
    return new_card

@app.put("/cards/{card_id}", response_model=Card)
def update_card(card_id: int, card_update: CardUpdate):
    cards = load_cards()
    for i, card in enumerate(cards):
        if card["id"] == card_id:
            if card_update.content is not None:
                card["content"] = card_update.content
            if card_update.tags is not None:
                card["tags"] = card_update.tags[:2]
            if card_update.source is not None:
                card["source"] = card_update.source
            card["updated_at"] = datetime.now().isoformat()
            cards[i] = card
            save_cards(cards)
            return card
    raise HTTPException(status_code=404, detail="卡片不存在")

@app.delete("/cards/{card_id}")
def delete_card(card_id: int):
    cards = load_cards()
    for i, card in enumerate(cards):
        if card["id"] == card_id:
            cards.pop(i)
            save_cards(cards)
            return {"message": "卡片已删除"}
    raise HTTPException(status_code=404, detail="卡片不存在")

@app.get("/cards/random")
def get_random_card():
    import random
    cards = load_cards()
    if not cards:
        raise HTTPException(status_code=404, detail="暂无卡片")
    return random.choice(cards)

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)