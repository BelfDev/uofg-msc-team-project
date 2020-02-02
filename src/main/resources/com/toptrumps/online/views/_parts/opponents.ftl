<div class="opponents">
    <#list players>
        <#items as player>
            <#include "ai-player.ftl">
        </#items>
    </#list>
</div>