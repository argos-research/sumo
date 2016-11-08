function IDCount = getIDCount()
%getIDCount Get the number of persons in the SUMO network.  

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getIDCount.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
IDCount = traci.person.getUniversal(constants.ID_COUNT, '');