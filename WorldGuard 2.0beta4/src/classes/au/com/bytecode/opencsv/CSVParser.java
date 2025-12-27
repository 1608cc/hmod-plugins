/*     */ package classes.au.com.bytecode.opencsv;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CSVParser
/*     */ {
/*     */   private final char separator;
/*     */   private final char quotechar;
/*     */   private final char escape;
/*     */   private final boolean strictQuotes;
/*     */   private String pending;
/*     */   private boolean inField = false;
/*     */   private final boolean ignoreLeadingWhiteSpace;
/*     */   public static final char DEFAULT_SEPARATOR = ',';
/*     */   public static final int INITIAL_READ_SIZE = 128;
/*     */   public static final char DEFAULT_QUOTE_CHARACTER = '"';
/*     */   public static final char DEFAULT_ESCAPE_CHARACTER = '\\';
/*     */   public static final boolean DEFAULT_STRICT_QUOTES = false;
/*     */   public static final boolean DEFAULT_IGNORE_LEADING_WHITESPACE = true;
/*     */   
/*     */   public CSVParser() {
/*  80 */     this(',', '"', '\\');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVParser(char separator) {
/*  89 */     this(separator, '"', '\\');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVParser(char separator, char quotechar) {
/* 102 */     this(separator, quotechar, '\\');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVParser(char separator, char quotechar, char escape) {
/* 115 */     this(separator, quotechar, escape, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes) {
/* 131 */     this(separator, quotechar, escape, strictQuotes, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CSVParser(char separator, char quotechar, char escape, boolean strictQuotes, boolean ignoreLeadingWhiteSpace) {
/* 149 */     this.separator = separator;
/* 150 */     this.quotechar = quotechar;
/* 151 */     this.escape = escape;
/* 152 */     this.strictQuotes = strictQuotes;
/* 153 */     this.ignoreLeadingWhiteSpace = ignoreLeadingWhiteSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPending() {
/* 160 */     return (this.pending != null);
/*     */   }
/*     */   
/*     */   public String[] parseLineMulti(String nextLine) throws IOException {
/* 164 */     return parseLine(nextLine, true);
/*     */   }
/*     */   
/*     */   public String[] parseLine(String nextLine) throws IOException {
/* 168 */     return parseLine(nextLine, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] parseLine(String nextLine, boolean multi) throws IOException {
/* 181 */     if (!multi && this.pending != null) {
/* 182 */       this.pending = null;
/*     */     }
/*     */     
/* 185 */     if (nextLine == null) {
/* 186 */       if (this.pending != null) {
/* 187 */         String s = this.pending;
/* 188 */         this.pending = null;
/* 189 */         return new String[] { s };
/*     */       } 
/* 191 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 195 */     List<String> tokensOnThisLine = new ArrayList<String>();
/* 196 */     StringBuilder sb = new StringBuilder(128);
/* 197 */     boolean inQuotes = false;
/* 198 */     if (this.pending != null) {
/* 199 */       sb.append(this.pending);
/* 200 */       this.pending = null;
/* 201 */       inQuotes = true;
/*     */     } 
/* 203 */     for (int i = 0; i < nextLine.length(); i++) {
/*     */       
/* 205 */       char c = nextLine.charAt(i);
/* 206 */       if (c == this.escape) {
/* 207 */         if (isNextCharacterEscapable(nextLine, !(!inQuotes && !this.inField), i)) {
/* 208 */           sb.append(nextLine.charAt(i + 1));
/* 209 */           i++;
/*     */         } 
/* 211 */       } else if (c == this.quotechar) {
/* 212 */         if (isNextCharacterEscapedQuote(nextLine, !(!inQuotes && !this.inField), i)) {
/* 213 */           sb.append(nextLine.charAt(i + 1));
/* 214 */           i++;
/*     */         } else {
/* 216 */           inQuotes = !inQuotes;
/*     */ 
/*     */           
/* 219 */           if (!this.strictQuotes && 
/* 220 */             i > 2 && 
/* 221 */             nextLine.charAt(i - 1) != this.separator && 
/* 222 */             nextLine.length() > i + 1 && 
/* 223 */             nextLine.charAt(i + 1) != this.separator)
/*     */           {
/*     */             
/* 226 */             if (this.ignoreLeadingWhiteSpace && sb.length() > 0 && isAllWhiteSpace(sb)) {
/* 227 */               sb = new StringBuilder(128);
/*     */             } else {
/* 229 */               sb.append(c);
/*     */             } 
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 235 */         this.inField = !this.inField;
/* 236 */       } else if (c == this.separator && !inQuotes) {
/* 237 */         tokensOnThisLine.add(sb.toString());
/* 238 */         sb = new StringBuilder(128);
/* 239 */         this.inField = false;
/*     */       }
/* 241 */       else if (!this.strictQuotes || inQuotes) {
/* 242 */         sb.append(c);
/* 243 */         this.inField = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 248 */     if (inQuotes) {
/* 249 */       if (multi) {
/*     */         
/* 251 */         sb.append("\n");
/* 252 */         this.pending = sb.toString();
/* 253 */         sb = null;
/*     */       } else {
/* 255 */         throw new IOException("Un-terminated quoted field at end of CSV line");
/*     */       } 
/*     */     }
/* 258 */     if (sb != null) {
/* 259 */       tokensOnThisLine.add(sb.toString());
/*     */     }
/* 261 */     return tokensOnThisLine.<String>toArray(new String[tokensOnThisLine.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i) {
/* 273 */     if (inQuotes && 
/* 274 */       nextLine.length() > i + 1 && 
/* 275 */       nextLine.charAt(i + 1) == this.quotechar) return true;
/*     */     
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i) {
/* 286 */     if (inQuotes && 
/* 287 */       nextLine.length() > i + 1 && (
/* 288 */       nextLine.charAt(i + 1) == this.quotechar || nextLine.charAt(i + 1) == this.escape)) return true;
/*     */     
/*     */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAllWhiteSpace(CharSequence sb) {
/* 297 */     boolean result = true;
/* 298 */     for (int i = 0; i < sb.length(); i++) {
/* 299 */       char c = sb.charAt(i);
/*     */       
/* 301 */       if (!Character.isWhitespace(c)) {
/* 302 */         return false;
/*     */       }
/*     */     } 
/* 305 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\1608c\Desktop\worldguard\WorldGuard.jar!\classes\au\com\bytecode\opencsv\CSVParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */