function subscriptionResults = getSubscriptionResults(personID)
%getSubscriptionResults Get the subscription results for the last time step.
%   subscriptionResults = getSubscriptionResults(PERSONID) Returns the 
%   subscription results for the last time step and the given person. If no 
%   person id is given, all subscription results are returned in a 
%   containers.Map data structure.
%   If the person id is unknown or the subscription did for any reason return
%   no data, 'None' is returned.
%   It is not possible to retrieve older subscription results than the ones
%   from the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getSubscriptionResults.m 25 2015-06-25 22:38:10Z afacostag $

global personSubscriptionResults
if isempty(personSubscriptionResults)
    throw(MException('traci:FatalTraCIError',...
        'You have to subscribe to the variable'));
end
if nargin < 1
    personID = 'None';
end

subscriptionResults = personSubscriptionResults.get(personID);