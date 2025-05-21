# generate_hashtags.py
import sys
import io
from keybert import KeyBERT

# 표준 출력 인코딩을 UTF-8로 강제 설정
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

kw_model = KeyBERT(model='all-MiniLM-L6-v2')

def generate_hashtags(text, num_tags=5):
    keywords = kw_model.extract_keywords(text, keyphrase_ngram_range=(1, 2), stop_words='english', top_n=num_tags)
    return [f"#{kw.replace(' ', '')}" for kw, score in keywords]

if __name__ == "__main__":
    title = sys.argv[1]
    content = sys.argv[2]
    text = f"{title} {content}"
    tags = generate_hashtags(text)
    print(",".join(tags))  # Java에서 이걸 받아서 파싱
