function subscribe(cmdID, subscriptionBegin, subscriptionEnd, objID, varIDs)
%subscribe An internal function to build a subscription message and parse
%the response.

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: subscribe.m 31 2016-09-28 15:16:56Z afacostag $

global message

% Construct the TraCI message
message.queue = [message.queue uint8(sscanf(cmdID,'%x'))];
len = 1+1+4+4+4+length(objID)+1+length(varIDs);
if len <= 255
    message.string = [message.string uint8(len)];
else
    message.string = [message.string uint8(0) traci.packInt32(len+4)];
end
message.string = [message.string uint8(sscanf(cmdID,'%x')),...
    traci.packInt32([length(objID) subscriptionEnd ...
    subscriptionBegin]) uint8(objID)];
message.string = [message.string uint8(length(varIDs))];
for v=1:length(varIDs)
    message.string = [message.string sscanf(varIDs{v},'%x')];
end

% Send the TraCI message and receive the result
result = traci.sendExact();

% Populate the subsctiptions and parse the result
[response, objectID] = traci.readSubscription(result);
if response - uint8(sscanf(cmdID,'%x'))~=16 || ~strcmp(objectID,objID) 
    raise(MException('traci:FatalTraciError',['Received answer ' response ...
        ', ' objectID 'for subscription command ' cmdID ', ' objID]));
end