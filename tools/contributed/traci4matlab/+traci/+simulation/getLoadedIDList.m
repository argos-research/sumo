function loadedIDList = getLoadedIDList()
%getLoadedIDList Get the IDs of loaded vehicles.
%   loadedIDList = getLoadedIDList() Returns a cell array of strings
%   cotaining the ids of the vehicles which were loaded in this time step.  

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getLoadedIDList.m 20 2015-03-02 16:52:32Z afacostag $

import traci.constants
loadedIDList = traci.simulation.getUniversal(constants.VAR_LOADED_VEHICLES_IDS);