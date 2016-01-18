function contextSubscriptionResults = getContextSubscriptionResults(edgeID)
%getContextSubscriptionResults Get the context subscription results for the
%   last time step.
%   contextSubscriptionResults = getContextSubscriptionResults(EDGEID) 
%   Returns the context subscription results for the last time step and the
%   given edge. If no edge id is given, all subscription results are 
%   returned in a containers.Map data struccure. If the edge id is unknown 
%   or the subscription did for any reason return no data, 'None' is 
%   returned.
%   It is not possible to retrieve older subscription results than the ones
%   from the last time step.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getContextSubscriptionResults.m 20 2015-03-02 16:52:32Z afacostag $

global edgeSubscriptionResults
if isempty(edgeSubscriptionResults)
    throw(MException('traci:FatalTraCIError',...
        'You have to subscribe to the variable'));
end
if nargin < 1
    edgeID=None;
end
contextSubscriptionResults = edgeSubscriptionResults.getContext(edgeID);